import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Thanks: http://www1.cs.columbia.edu/~bert/courses/3137/hw3_files/GraphDraw.java
 */
public class Visualizer extends JFrame {

    int viewWidth;
    int viewHeight;
    int width;
    int height;
    double scaleW;
    double scaleH;

    JButton btnSelect;
    JLabel lblName, lblNameFile;

    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    double[][] coordinates;

    private JLabel stats;
    final JFileChooser fileDialog = new JFileChooser();
    String nameFile;


    public Visualizer(Program program) {
        doshow(program);
    }

    public void doshow(Program program) {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test");
        this.stats = new JLabel();
        this.pack();
//        this.setLocationRelativeTo(null);
        this.add(stats, BorderLayout.SOUTH);
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);

        JPanel pnCeneter = new JPanel();
        JButton btnSelect = new JButton();
        pnCeneter.add(new JLabel("Control in action: JFileChooser"));
        pnCeneter.add(btnSelect = new JButton("Chooser File"));
        pnCeneter.add(lblName = new JLabel("File Selected: "));
        pnCeneter.add(lblNameFile = new JLabel(""));
        JFrame mainFrame = this;
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileDialog.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileDialog.getSelectedFile();
                    setNameFile(file.getName());
                    lblNameFile.setText(getNameFile());
                }
            }
        });
        this.add(pnCeneter);
        try { Thread.sleep(1000); } catch (Exception ex) {}
    }

    public String getNameFile() {
        if (nameFile != null) {
            if (nameFile.equalsIgnoreCase("")) {
                return "test";
            }
            return nameFile;
        } else {
            return "test";
        }
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public void createData() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        viewWidth = this.getWidth();
        viewHeight = this.getHeight();
        width = 1;
        height = 1;
        for(int i = 0; i < coordinates.length; i++) {
            if(coordinates[i][0] > scaleW) scaleW = (int) coordinates[i][0];
            if(coordinates[i][1] > scaleH) scaleH = (int) coordinates[i][1];
        }
        scaleW = viewWidth / scaleW;
        scaleH = viewHeight / scaleH;
        scaleW *= .9;
        scaleH *= .9;
    }

    public Visualizer(double[][] coordinates) {
        super();
        this.coordinates = coordinates;
    }

    public void asignData(double[][] coordinates) {
        this.coordinates = coordinates;
    }


    public void draw(int[] tour) {
        this.nodes.clear();
        this.edges.clear();

        for(int i = 0; i < coordinates.length; i++) {
            int x = (int) (coordinates[i][0] * scaleW);
            int y = (int) (coordinates[i][1] * scaleH);
            this.addNode(String.valueOf(i), x, y);
        }
        for(int i = 0; i < tour.length - 1; i++) {
            this.addEdge(tour[i], tour[i + 1]);
        }
        this.repaint();
        System.out.println("draw");
    }

    public void setStat(String text) {
        this.stats.setText(text);
    }

    class Node {
        int x, y;
        String name;

        public Node(String myName, int myX, int myY) {
            x = myX;
            y = myY;
            name = myName;
        }
    }

    class edge {
        int i,j;

        public edge(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    // Add a node at pixel (x,y)
    public void addNode(String name, int x, int y) {
        nodes.add(new Node(name,x,y));
    }

    // Add an edge between nodes i and j
    public void addEdge(int i, int j) {
        edges.add(new edge(i,j));
    }

    // Clear and repaint the nodes and edges
    public void paint(Graphics g) {
        super.paint(g);
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());
        g.setColor(Color.black);
        for (edge e : edges) {
            g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);
        }
        for (Node n : nodes) {
            int nodeWidth = Math.max(width, f.stringWidth(n.name)+width/2);
            g.setColor(Color.white);
            g.fillOval(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x-nodeWidth/2, n.y-nodeHeight/2, nodeWidth, nodeHeight);
            g.drawString(n.name, n.x-f.stringWidth(n.name)/2,n.y+f.getHeight()/2);
        }
    }
}