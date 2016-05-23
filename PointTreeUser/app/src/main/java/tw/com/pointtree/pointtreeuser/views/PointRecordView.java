package tw.com.pointtree.pointtreeuser.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import tw.com.pointtree.pointtreeuser.R;
import tw.com.pointtree.pointtreeuser.models.Transaction;
import tw.com.pointtree.pointtreeuser.models.User;

public class PointRecordView extends LinearLayout {
    private TextView dateTextView;
    private TextView timeTextView;
    private TextView amountTextView;
    private TextView recordTextView;
    private Context context;
    private User currentUser;

    public PointRecordView(Context context, User currentUser) {
        super(context);
        this.context = context;
        this.currentUser = currentUser;
        initializeViews();
    }

    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.point_record, this);

        dateTextView = (TextView) this.findViewById(R.id.dateTextView);
        timeTextView = (TextView) this.findViewById(R.id.timeTextView);
        amountTextView = (TextView) this.findViewById(R.id.amountTextView);
        recordTextView = (TextView) this.findViewById(R.id.recordTextView);
    }


    public void setDateTextViewVisible(boolean visible) {
        if (visible) {
            dateTextView.setVisibility(View.VISIBLE);
        } else {
            dateTextView.setVisibility(View.GONE);
        }
    }

    public void setData(Transaction transaction) {
        setDateTextView(transaction);
        setTimeTextView(transaction);
        setAmountTextView(transaction);
        setRecordTextView(transaction);
    }

    private void setDateTextView(Transaction transaction) {
        Date date = new Date(Long.parseLong(transaction.getTimestamp()) * 1000);
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy.MM.dd");
        dateTextView.setText(sdFormat.format(date));
        dateTextView.setTextColor(ContextCompat.getColor(context, R.color.coolGrey));
    }

    private void setTimeTextView(Transaction transaction) {
        Date date = new Date(Long.parseLong(transaction.getTimestamp()) * 1000);
        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm");
        timeTextView.setText(sdFormat.format(date));
        timeTextView.setTextColor(ContextCompat.getColor(context, R.color.coolGrey));
    }

    private void setAmountTextView(Transaction transaction) {
        StringBuilder amountString = new StringBuilder();

        if (currentUser != null && currentUser.equals(transaction.getReceiver())) {
            amountString.append("+").append(Integer.toString(transaction.getAmount())).append("點");
            amountTextView.setTextColor(ContextCompat.getColor(context, R.color.tealish));
        } else {
            amountString.append("-").append(Integer.toString(transaction.getAmount())).append("點");
            amountTextView.setTextColor(ContextCompat.getColor(context, R.color.warmPink));
        }
        amountTextView.setText(amountString.toString());
    }

    private void setRecordTextView(Transaction transaction) {
        SpannableStringBuilder sb = new SpannableStringBuilder();

        if (currentUser.equals(transaction.getReceiver())) {
            if (transaction.getSender().getType().equals(User.TYPE_STORE)) {
                sb = getColoredClickableSb(R.color.tealish, null, transaction.getPoint().getName());
            } else if (transaction.getSender().getType().equals(User.TYPE_NORMAL)) {
                sb.append("收到 ");
                sb.append(getColoredClickableSb(R.color.fadedBlue,
                        getUserClickableSpan(transaction.getSender()), transaction.getSender().getName()));
                sb.append(" 送來 ");
                sb.append(getColoredClickableSb(R.color.tealish, null, transaction.getPoint().getName()));
                sb.append(" 的點數");
            }
        } else if (currentUser.equals(transaction.getSender())) {
            if (transaction.getReceiver().getType().equals(User.TYPE_STORE)) {
                sb.append("兌換 ");
                sb.append(getColoredClickableSb(R.color.warmPink, null, transaction.getPoint().getName()));
            } else if (transaction.getReceiver().getType().equals(User.TYPE_NORMAL)) {
                sb.append("送 ");
                sb.append(getColoredClickableSb(R.color.warmPink, null, transaction.getPoint().getName()));
                sb.append(" 的點數給 ");
                sb.append(getColoredClickableSb(R.color.fadedBlue,
                        getUserClickableSpan(transaction.getReceiver()), transaction.getReceiver().getName()));
            }
        }

        // To enable click on string
        recordTextView.setMovementMethod(LinkMovementMethod.getInstance());
        recordTextView.setText(sb);
    }

    private SpannableStringBuilder getColoredClickableSb(int color, ClickableSpan cs, String string) {
        SpannableStringBuilder sb = new SpannableStringBuilder(string);
        ForegroundColorSpan colorSpan =
                new ForegroundColorSpan(ContextCompat.getColor(context, color));

        sb.setSpan(cs, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(colorSpan, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    private ClickableSpan getUserClickableSpan(final User user) {
        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                // TODO real onClick event
                Log.d("CLICKED!", user.getName());
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false); // remove underline
            }
        };
        return cs;
    }
}
