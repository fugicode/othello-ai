package jaima.nn;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIDefaults;

/**
 * Write a description of class GuiFF here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GuiFF
extends FeedForward {

    /**
     * Constructor for objects of class GuiFF
     */
    public GuiFF(int[] dimensions)
    {
        super(dimensions);
    }

    // global variables.  Tsk, tsk!!
    Graphics g;
    Rectangle bounds;
    Color bgColor;
    int left;
    int top;
    int height;
    int width;
    int maxRows;
    int nodeDiameter;
    int yGap;
    int cols;

    public void setGlobals(JPanel panel) {
        bounds = panel.getBounds();
        bgColor = panel.getBackground();

        left = (int) bounds.getX();
        top = (int) bounds.getY();
        height = (int) bounds.getHeight();
        width = (int) bounds.getWidth();
        maxRows = nodesTall();
        nodeDiameter = Math.min(
            height / (4 * maxRows / 3),
            width / (4 * nodes.length / 3)
        );
        int yFudge = 0;
        yGap = ((height - yFudge) / maxRows) - nodeDiameter;
        cols = nodes.length;
    }

    /**
     * Paint method for applet.
     * 
     * @param  panel The swing panel into which 
     * this network is drawn.
     */
    public void paint(JPanel panel, Graphics g)
    {
        this.g = g;
        setGlobals(panel);

        // draw the arcs
        for(int i = 1; i < cols; i++) {
            int lCol = i - 1;
            int rCol = i;
            int leftRows = (nodes[i - 1].length - 1);
            int rightRows = (nodes[i].length - 1);
            for(int j = 0; j < leftRows; j++) {
                for(int k = 0; k < rightRows; k++) {

                    int colWidth = width / cols;
                    int leftRowHeight = height / leftRows;
                    int lbx = colWidth * lCol + left;
                    int lby = leftRowHeight * j + top;
                    int lcx = lbx + colWidth / 2;
                    int lcy = lby + leftRowHeight / 2;

                    int rightRowHeight = height / rightRows;
                    int rbx = colWidth * rCol + left;
                    int rby = rightRowHeight * k + top;
                    int rcx = rbx + colWidth / 2;
                    int rcy = rby + rightRowHeight / 2;

                    g.drawLine(lcx, lcy, rcx, rcy);

                    String lineLabel = makeLabel(nodes[i][k+1].getWeight(j+1), 5);
                    int num = k + 1;
                    int denom = rightRows + 1;
                    int labelW = num * (colWidth/denom - nodeDiameter/2);
                    int labelH = leftRowHeight / denom;
                    Rectangle labelSpace = new Rectangle(0, 0, labelW, labelH);
                    if(!canFit(lineLabel, labelSpace)) {
                        continue;
                    }
                    labelLine(lineLabel, lcx, lcy, rcx, rcy, num, denom);
                }
            }
        }

        // draw the nodes
        for(int i = 0; i < cols; i++) {
            int rows = (nodes[i].length - 1);
            for(int j = 0; j < rows; j++) {

                int colWidth = width / cols;
                int rowHeight = height / rows;
                int bx = colWidth * i + left;
                int by = rowHeight * j + top;
                // g.setColor(Color.black);
                // g.drawRect(bx, by, colWidth, rowHeight);

                int cx = bx + colWidth / 2;
                int cy = by + rowHeight / 2;

                paintNode(nodes[i][j+1], cx, cy, nodeDiameter);
            }
        }
    }

    private String makeLabel(Double weight, int maxLength) {

        String wLabel = weight + "";
        if(wLabel.length() > maxLength) {
            wLabel = wLabel.substring(0, maxLength);
        }
        return wLabel;
    }

    private void labelLine(String label, int lx, int ly, int rx, int ry, int n, int d) {
        int cx = lx + n * (rx - lx) / d;
        int cy = ly + n * (ry - ly) / d;

        paintLabel(cx, cy, label, "", bgColor);
    }

    public Color colorValue(Double value) {
        if(value == null) {
            return new Color(153, 153, 153);
        }
        
        int rp = 153;
        int gp = 153;
        int bp = 255;
        
        int rm = 255;
        int gm = 51;
        int bm = 51;
        
        int r = rm + ((int) ((rp - rm) * (value + 1))) / 2;
        int g = gm + ((int) ((gp - gm) * (value + 1))) / 2;
        int b = bm + ((int) ((bp - bm) * (value + 1))) / 2;
        
        return new Color(r, g, b);
    }
    
    private void paintNode(Neuron node, int cx, int cy, int dia) {
        // paint the node, with its current output in the center.
        Double value = node.getValue();
        Color fill = colorValue(value);
        paintCircle(cx, cy, dia, fill);

        g.setColor(Color.black);
        String vLabel = value + "";
        if(vLabel.length() > 5) {
            vLabel = vLabel.substring(0, 5);
        }
        
        Rectangle vLabelSpace = new Rectangle(0, 0, dia, dia);
        if(!canFit(vLabel, vLabelSpace)) {
            return;
        }

        // paint the bias underneath
        if(node.numberOfConnections() > 0) {
            Double bias = node.getWeight(0);
            String bLabel = bias + "";
            if(bLabel.length() > 5) {
                bLabel = bLabel.substring(0, 5);
            }
            int tx = cx;
            int ty = cy + dia / 2 + (int) g.getFontMetrics().getAscent() / 2 + 2;

            // is there room under the node for the bias label?
            Rectangle blabelSpace = new Rectangle(0, 0, nodeDiameter, yGap);
            if(canFit(bLabel, blabelSpace)) {
                paintLabel(tx, ty, bLabel);
            }
        }

        // paint the target to the right
        Double target = node.getTarget();
        String tLabel = "";
        if(target != null && !approxEqual(target, value)) {
            int tx = cx + dia / 2;
            int ty = cy;

            tLabel += target;
            if(tLabel.length() > 5) {
                tLabel = tLabel.substring(0, 5);
            }
            if(value < target) {
                tLabel = " < " + tLabel;
            } else if(value > target) {
                tLabel = " > " + tLabel;
            }
        }
        paintLabel(cx, cy, vLabel, tLabel);
    }

    private static boolean approxEqual(double x, double y) {
        double epsilon = Math.max(1, Math.abs(Math.max(x, y))) * 0.015;
        return Math.abs(x - y) < epsilon;
    }

    private int paintedWidth(String s) {
        return (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
    }

    private int paintedHeight() {
        return (int) g.getFontMetrics().getAscent(); 
    }

    private boolean canFit(String s, Rectangle box) {
        if(paintedWidth(s) > box.width) 
            return false;

        if(paintedHeight() > box.height) 
            return false;

        return true;
    }

    private void paintLabel(int cx, int cy, String text) {
        paintLabel(cx, cy, text, "", null);
    }

    private void paintLabel(int cx, int cy, String centered, String appended) {
        paintLabel(cx, cy, centered, appended, null);
    }

    private void paintLabel(int cx, int cy, String centered, String appended, Color bg) {
        // Font font = UIManager.getLookAndFeelDefaults().getFont("Label.font");
        int x = cx - paintedWidth(centered)/2;  
        int y = cy + paintedHeight()/2;

        if(bg != null) { 
            int bx = x;
            int bw = paintedWidth(centered);
            int by = cy - paintedHeight()/2;
            int bh = paintedHeight();

            g.setColor(bg);
            g.fillRect(bx, by, bw, bh);
        }

        g.setColor(Color.black);
        g.drawString(centered + appended, x, y);
    }

    private void paintCircle(int cx, int cy, int dia, Color fill) {
        g.setColor(fill);
        g.fillOval(cx - dia/2, cy - dia/2, dia, dia);
        g.setColor(Color.black);
        g.drawOval(cx - dia/2, cy - dia/2, dia, dia);
    }

    public static void printDefaults() {
        UIDefaults defaults = UIManager.getLookAndFeelDefaults( );
        for (Object key: defaults.keySet( ) )
        {
            if(key.toString().toLowerCase().contains("font")) {
                System.out.println( key.toString( ) + ":  " +
                    defaults.get( key ).toString( ) );
            }
        }
    }
}
