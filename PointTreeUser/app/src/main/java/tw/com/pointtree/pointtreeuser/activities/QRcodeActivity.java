package tw.com.pointtree.pointtreeuser.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import tw.com.pointtree.pointtreeuser.R;

public class QRcodeActivity extends AppCompatActivity {
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    // TODO: should be user number
    public final static String STR = "A string to be encoded as QR code";

    private ImageView qrcodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        setQRcodeView();
    }

    private void setQRcodeView() {
        qrcodeImage = (ImageView) findViewById(R.id.qrcode_image);

        try {
            Bitmap bitmap = encodeAsBitmap(STR);
            qrcodeImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                bitmap.setPixel(x, y, result.get(x, y) ? BLACK : WHITE);
            }
        }

        return bitmap;
    }
}
