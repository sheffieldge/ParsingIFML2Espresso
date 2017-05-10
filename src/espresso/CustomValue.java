package espresso;

/**
 * Created by gexiaofei on 2017/5/10.
 */
public class CustomValue {
    String plainText;
    int recyclerViewItemPosition;
    int recyclerViewItemCount;

    public CustomValue() {
    }

    public CustomValue(String plainText, int recyclerViewItemPosition) {
        this.plainText = plainText;
        this.recyclerViewItemPosition = recyclerViewItemPosition;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public int getRecyclerViewItemPosition() {
        return recyclerViewItemPosition;
    }

    public void setRecyclerViewItemPosition(int recyclerViewItemPosition) {
        this.recyclerViewItemPosition = recyclerViewItemPosition;
    }
}
