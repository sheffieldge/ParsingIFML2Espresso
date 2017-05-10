package espresso;

/**
 * Espresso Check 所需的多种类型值
 */
public class CustomValue {
    String plainText;
    String displayText; // 对应TextView
    String errorText;
    int recyclerViewItemPosition;
    int recyclerViewItemCount;
    boolean shouldOpen;

    public CustomValue() {
    }

    public boolean isShouldOpen() {
        return shouldOpen;
    }

    public void setShouldOpen(boolean shouldOpen) {
        this.shouldOpen = shouldOpen;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public int getRecyclerViewItemPosition() {
        return recyclerViewItemPosition;
    }

    public void setRecyclerViewItemPosition(int recyclerViewItemPosition) {
        this.recyclerViewItemPosition = recyclerViewItemPosition;
    }

    public int getRecyclerViewItemCount() {
        return recyclerViewItemCount;
    }

    public void setRecyclerViewItemCount(int recyclerViewItemCount) {
        this.recyclerViewItemCount = recyclerViewItemCount;
    }
}
