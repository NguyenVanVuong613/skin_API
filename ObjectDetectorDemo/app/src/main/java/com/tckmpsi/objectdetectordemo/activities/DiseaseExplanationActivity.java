package com.tckmpsi.objectdetectordemo.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.tckmpsi.objectdetectordemo.R;

public class DiseaseExplanationActivity extends AppCompatActivity {
    private TextView explanationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_explanation);

        explanationTextView = findViewById(R.id.explanationText);
        ImageView diseaseImageView = findViewById(R.id.disease_image);

        // Lấy tên bệnh từ Intent
        String diseaseName = getIntent().getStringExtra("disease_name");

        // Kiểm tra nếu bệnh có tên hợp lệ thì hiển thị thông tin bệnh
        if (diseaseName != null) {
            int imageResId = getDiseaseImageResource(diseaseName);
            diseaseImageView.setImageResource(imageResId);

            String explanation = getExplanationForDisease(diseaseName);
            explanationTextView.setText(explanation);
        } else {
            diseaseImageView.setImageResource(R.drawable.download);
            explanationTextView.setText("Không có thông tin về bệnh.");
        }
    }

    private int getDiseaseImageResource(String diseaseName) {
        switch (diseaseName) {
            case "Khong_benh":
                return R.drawable.khongbenh;
            case "Hac_to":
                return R.drawable.ungthu_hacto;
            case "Vay":
                return R.drawable.ungthu_tebaovay;
            case "Day":
                return R.drawable.ungthu_tebaoday;
            case "Benh_khac":
                return R.drawable.benh_khac;
            default:
                return R.drawable.download;
        }
    }
    private String getExplanationForDisease(String diseaseName) {
        switch (diseaseName) {
            case "Khong_benh":
                return "Da không bệnh: Đây là kết quả khi không phát hiện dấu hiệu của bệnh da.\n";

            case "Hac_to":
                return "Bệnh ung thư da hắc tố: Ung thư hắc tố da (Melanoma) là ung thư da ác tính nhất, phát triển từ các tế bào sản xuất melanin (melanocytes). Ung thư hắc tố cũng có thể biểu hiện ở vị trí ngoài da như mắt hoặc hiếm hơn là cơ quan nội tạng (ví dụ: ruột). Nguy cơ mắc ung thư hắc tố da dường như đang gia tăng ở người dưới 40 tuổi, đặc biệt là phụ nữ.\n" +
                        "\nNguyên nhân gây ung thư hắc tố da:\n" +
                        " - Da trắng: Có ít các sắc tố (melanin) trên da có nghĩa rằng bạn có ít khả năng bảo vệ khỏi các bức xạ UV hơn. Người tóc vàng, người dễ dàng có các tàn nhang, rám nắng có nguy cơ phát triển ung thư hắc tố hơn người da đen.\n" +
                        " - Tiền sử rám nắng: tăng nguy cơ ung thư hắc tố.\n" +
                        " - Sống vùng gần xích đạo: có nguy cơ tiếp xúc trực tiếp với ánh nắng mặt trời với năng lượng bức xạ cao hơn, phơi nhiễm với tia UV nhiều hơn.\n" +
                        " - Có nhiều nốt ruồi hoặc các nốt ruồi không bình thường: Có nhiều hơn 50 nốt ruồi là yếu tố nguy cơ của ung thư hắc tố. Tương tự, có nhiều các nốt ruồi bất thường cũng là yếu tố nguy cơ của bệnh.\n" +
                        " - Tiền sử gia đình bị ung thư hắc tố: người có tiền sử gia đình mắc ung thư hắc tố, đặc biệt là người thân thế hệ 1 như bố mẹ, anh chị em có nguy cơ mắc bệnh cao hơn.\n" +
                        " - Suy giảm hệ miễn dịch: người có hệ miễn dịch bị suy giảm có nguy cơ ung thư hắc tố da cao hơn.\n" +
                        "\nPhòng ngừa bệnh Ung thư hắc tố da:\n" +
                        " - Tránh tiếp xúc với ánh nắng mặt trời giai đoạn giữa trưa nắng: Tiếp xúc với ánh nắng mặt trời tích tụ theo thời gian sẽ làm tăng nguy cơ ung thư hắc tố da. Hạn chế các hoạt động ngoài trời tại thời điểm ánh nắng chiếu mạnh nhất (từ 11-14h trưa) là cách tốt nhất phòng tránh các tổn thương da như cháy nắng, rám nắng cũng như phòng tránh ung thư hắc tố da.\n" +
                        " - Sử dụng kem chống nắng: kem chống nắng không thể bảo vệ hoàn toàn trước các tác hại của tia UV đặc biệt là các bức xạ dẫn đến ung thư hắc tố da. Tuy nhiên sử dụng kem chống nắng vẫn có vai trò quan trọng. Bác sĩ da liễu khuyến cáo sử dụng kem chống nắng có chỉ số SPF ít nhất là 30.\n" +
                        " - Mặc quần áo bảo hộ và đeo kính chống nắng khi ra ngoài.\n" +
                        " - Một điều quan trọng là chăm sóc làn da của mình để nhận biết các thay đổi trên cơ thể: thay đổi nốt ruồi cũ, thay đổi màu sắc một vùng da.\n";

            case "Vay":
                return "Ung thư tế bào vảy: Ung thư tế bào vảy (SCC) hay còn được gọi là ung thư biểu mô tế bào vảy, là một loại ung thư da bắt đầu từ các tế bào vảy.\n" +
                        "\nNguyên nhân ung thư tế bào vảy:\n" +
                        " - Tiếp xúc với ánh nắng quá nhiều: Tiếp xúc với tia UV từ mặt trời làm tăng nguy cơ ung thư biểu mô tế bào vảy của da.\n" +
                        " - Dành nhiều thời gian dưới ánh nắng mặt trời, đặc biệt nếu bạn không che chắn da bằng quần áo hoặc kem chống nắng, sẽ làm tăng nguy cơ ung thư biểu mô tế bào vảy của da nhiều hơn.\n" +
                        " - Sử dụng giường tắm nắng: Những người sử dụng giường tắm nắng trong nhà có nguy cơ mắc ung thư biểu mô tế bào vảy trên da cao hơn.\n" +
                        " - Tiền sử cháy nắng: Từng bị một hoặc nhiều vết cháy nắng phồng rộp khi còn nhỏ hoặc thiếu niên sẽ làm tăng nguy cơ phát triển ung thư biểu mô tế bào vảy của da khi trưởng thành.\n" +
                        " - Hệ thống miễn dịch suy giảm: Những người có hệ thống miễn dịch suy giảm có nguy cơ ung thư da cao hơn.\n" +
                        "\nPhòng ngừa ung thư tế bào vảy:\n" +
                        " - Tránh nắng vào giữa ngày: Đối với một số vùng, tia nắng mặt trời mạnh nhất trong khoảng từ 10 giờ sáng đến 3 giờ chiều.\n" +
                        " - Thoa kem chống nắng thường xuyên: Sử dụng kem chống nắng phổ rộng với chỉ số SPF ít nhất là 30.\n" +
                        " - Mặc quần áo chống nắng: Che phủ làn da của bạn bằng quần áo tối màu, dệt chặt chẽ che cánh tay và chân của bạn và đội mũ rộng vành.\n" +
                        " - Tránh giường tắm nắng: Giường tắm nắng phát ra tia UV và có thể làm tăng nguy cơ ung thư da.\n" +
                        " - Kiểm tra da của bạn thường xuyên và báo cáo những thay đổi cho bác sĩ của bạn.\n";

            case "Day":
                return "Bệnh tế bào đáy: Ung thư biểu mô tế bào đáy là loại ung thư khởi phát từ tế bào đáy – loại tế bào trong da có chức năng tạo ra tế bào mới thay thế cho tế bào cũ. Đây cũng là loại ung thư da phổ biến nhất, bệnh thường xuất hiện dưới dạng các nốt trên bề mặt da, có thể chảy máu.\n" +
                        "\nNguyên nhân ung thư tế bào đáy:\n" +
                        " - Ánh sáng mặt trời: Tình trạng cháy nắng do ánh nắng mặt trời hoặc tia bức xạ UV có trong các giường tắm nắng.\n" +
                        " - Xạ trị: Xạ trị có thể dẫn đến các vùng da bị tổn thương, về lâu dài phát triển thành ung thư biểu mô đáy.\n" +
                        " - Chủng tộc da trắng: Người da trắng có ít tế bào hắc tố melanin hơn, khiến dễ bị ảnh hưởng bởi các tác nhân gây ung thư.\n" +
                        "\nPhòng ngừa bệnh Ung thư tế bào đáy:\n" +
                        " - Ở trong nhà hoặc dưới bóng râm vào thời điểm giữa ngày (10-15h) nhằm tránh tia UV mạnh nhất trong ngày.\n" +
                        " - Thoa kem chống nắng có chỉ số bảo vệ cao SPF30+.\n" +
                        " - Mặc quần áo che chắn kỹ càng, kết hợp sử dụng kính râm và mũ rộng vành.\n" +
                        " - Tránh sử dụng giường tắm nắng.\n" +
                        " - Uống nicotinamide (vitamin B3) có thể làm giảm nguy cơ của ung thư tế bào đáy.\n";

            case "Benh_khac":
                return "Bệnh da khác: Là những loại bệnh khác không thuộc các nhóm đã liệt kê.";

            default:
                return "Không có thông tin.";
        }
    }

}
