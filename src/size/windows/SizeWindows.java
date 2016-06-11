package size.windows;

import java.awt.Dimension;
import java.awt.Toolkit;

public class SizeWindows {

    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int w;
    private final int h;
    private final int h_b;
    private final int win_w;
    private final int win_h;

    public SizeWindows() {
        w = screenSize.width;
        h = screenSize.height;
        h_b=screenSize.height / 2 + 300;
        win_w = screenSize.width / 2 - 80;
        win_h = screenSize.height / 3 - 180;
    }

    public int getWin_w() {
        return win_w;
    }

    public int getWin_h() {
        return win_h;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public int getH_b() {
        return h_b;
    }

}
