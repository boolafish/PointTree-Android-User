package tw.com.pointtree.pointtreeuser.activities;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.Session;
import tw.com.pointtree.pointtreeuser.api.models.User;

public class QRcodeActivity extends TitledActivity {
    private final static int WHITE = 0xFFFFFFFF;
    private final static int BLACK = 0xFF000000;
    private final static int WIDTH = 400;
    private final static int HEIGHT = 400;

    private Session session;

    private ImageView qrcodeImage;

    @NonNull
    @Override
    public String getActivityTitle() {
        return "QRcode 集點";
    }

    @NonNull
    @Override
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.activity_qrcode, container, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new Session(this);

        setQRcodeView();
    }

    private void setQRcodeView() {
        qrcodeImage = (ImageView) findViewById(R.id.qrcode_image);

        String str = Integer.toString(session.getUser().getUserNumber());

        try {
            Bitmap bitmap = encodeAsBitmap(str);
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
