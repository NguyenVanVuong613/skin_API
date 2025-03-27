package com.tckmpsi.objectdetectordemo.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tckmpsi.objectdetectordemo.R;
import com.tckmpsi.objectdetectordemo.models.Disease;
import com.tckmpsi.objectdetectordemo.models.DiseaseDetail;
import com.tckmpsi.objectdetectordemo.network.NetworkClient;
import com.tckmpsi.objectdetectordemo.utils.ImageUtils;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {

                Toast.makeText(this,
                        "Cần cấp quyền để sử dụng camera",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private static final String DEFAULT_MODEL = "resnet50";

    // Views
    private ImageView imageView;
    private TextView resultTextView;
    private ProgressBar progressBar;
    private Button explanationButton;
    private Button classifyButton;
    private String currentModel = DEFAULT_MODEL;
    private String base64Image;
    private String currentDisease;

    // Activity result launchers
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> handleImageResult(result.getResultCode(), result.getData(), true)
    );

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> handleImageResult(result.getResultCode(), result.getData(), false)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        imageView = findViewById(R.id.image);
        resultTextView = findViewById(R.id.result_text);
        progressBar = findViewById(R.id.progressBar);
        classifyButton = findViewById(R.id.detect);
        classifyButton.setEnabled(false);
        explanationButton = findViewById(R.id.explanation_button);
    }

    private void setupClickListeners() {
        findViewById(R.id.camera_button).setOnClickListener(v -> openCamera());
        findViewById(R.id.gallery_button).setOnClickListener(v -> openGallery());
        classifyButton.setOnClickListener(v -> classifyImage());
        explanationButton.setOnClickListener(v -> openDiseaseExplanation());
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            cameraLauncher.launch(intent);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void handleImageResult(int resultCode, Intent data, boolean isCamera) {
        if (resultCode != RESULT_OK || data == null) return;

        try {
            Bitmap bitmap;
            if (isCamera) {
                bitmap = (Bitmap) data.getExtras().get("data");
            } else {
                Uri selectedImageUri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            }

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                base64Image = ImageUtils.convertBitmapToBase64(bitmap);
                classifyButton.setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Lỗi xử lý ảnh: " + e.getMessage());
        }
    }

    private void openDiseaseExplanation() {
        if (currentDisease != null) {
            Intent intent = new Intent(MainActivity.this, DiseaseExplanationActivity.class);
            intent.putExtra("disease_name", currentDisease); // Pass the disease name
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Vui lòng phân loại bệnh trước", Toast.LENGTH_SHORT).show();
        }
    }
    private void classifyImage() {
        progressBar.setVisibility(View.VISIBLE);
        classifyButton.setEnabled(false);
        NetworkClient.sendImageData(base64Image, currentModel, new DiseaseClassificationCallback());
    }

    private void showError(String message) {
        resultTextView.setText("Lỗi: " + message);
    }

    private class DiseaseClassificationCallback implements NetworkClient.DiseaseCallback {
        @SuppressLint("DefaultLocale")
        @Override
        public void onSuccess(@NonNull Disease disease) {
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                classifyButton.setEnabled(true);
                explanationButton.setEnabled(true);
                displayResults(disease);
                currentDisease = disease.getDisease(); // Store the detected disease name
            });
        }

        @Override
        public void onFailure(String errorMessage) {
            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                classifyButton.setEnabled(true);
                showError(errorMessage);
                explanationButton.setEnabled(false);
            });
        }

        private void displayResults(@NonNull Disease disease) {
            StringBuilder resultText = new StringBuilder()
                    .append("\nDa của bạn có dấu hiệu của bệnh: ")
                    .append(getVietnameseDieaseName(disease.getDisease()))
                    .append("\nĐộ tự tin: ")
                    .append(String.format("%.2f%%", disease.getScore() * 100));

            DiseaseDetail detail = disease.getDetail();
            if (detail != null) {
                resultText.append("\n\nChi tiết phân tích:\n");
                appendDiseaseDetail(resultText, "Khong_benh", detail.getKhong_benh());
                appendDiseaseDetail(resultText, "Hac_to", detail.getHac_to());
                appendDiseaseDetail(resultText, "Vay", detail.getVay());
                appendDiseaseDetail(resultText, "Day", detail.getDay());
                appendDiseaseDetail(resultText, "Ung_thu", detail.getUng_thu());
                appendDiseaseDetail(resultText, "Benh_khac", detail.getBenh_khac());
            }

            resultTextView.setText(resultText.toString());
        }

        private void appendDiseaseDetail(StringBuilder builder, String diseaseName, double value) {
            if (value > 0) {
                builder.append(getVietnameseDieaseName(diseaseName))
                        .append(": ")
                        .append(String.format("%.2f%%", value * 100))
                        .append("\n");
            }
        }

        private String getVietnameseDieaseName(String technicalName) {
            switch (technicalName) {
                case "Khong_benh":
                    return "Không bệnh";
                case "Hac_to":
                    return "Bệnh hắc tố";
                case "Vay":
                    return "Bệnh vảy";
                case "Day":
                    return "Bệnh tế bào đáy";
                case "Ung_thu":
                    return "Da ung thư";
                case "Benh_khac":
                    return "Bệnh da khác";
                default:
                    return technicalName;
            }
        }

    }
}