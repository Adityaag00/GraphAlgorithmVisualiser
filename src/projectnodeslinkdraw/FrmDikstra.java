
package projectnodeslinkdraw;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class FrmDikstra extends JPanel implements Runnable{
    public static int V;
    JFrame main;
    ProjectNodesLinkDraw dr;
    JPanel pnl;
    int graph[][];
    int partialy=-1;
    int source=-1;
    int visited=-1;
    int edgesel=-1;
    Boolean sptSet[];
    int ballin=-1,ballfin=-1;
    pnlMatrix pnlMa;
    int dist[];
    
    public FrmDikstra(ProjectNodesLinkDraw dr) {
        init();
        this.dr=dr;
        V=dr.balls.size()+1;
        setUp();
        sptSet=new Boolean[V];
        pnlMa=new pnlMatrix(this);
        main.getContentPane().add(pnlMa);
        Thread thrd = new Thread(pnlMa);
        thrd.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0, 0, 1080, 720);
        g.setColor(Color.BLACK);
        Graphics2D g2= (Graphics2D)g;
        for(int i=0;i<1080;i+=100) {
            g.drawLine(i, 0, i, 720);
        }
        for(int i=0;i<720;i+=100) {
            g.drawLine(0, i, 1080, i);
        }
        for(ProjectNodesLinkDraw.Ball x:dr.balls) {
            if(x.id==source) {
                g.setColor(Color.pink);
            }
            else if(sptSet[x.id]) {
                g.setColor(Color.YELLOW);
            }
            else if(x.id==partialy) {
                g.setColor(Color.LIGHT_GRAY);
            }
            else 
                g.setColor(Color.RED);
            g.fillOval(x.x-15, x.y-15, 30, 30);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Monospaced", Font.BOLD, 14));
            g.drawString(""+x.id, x.x-4, x.y+5);
        }
        g.setColor(Color.GREEN);
        g2.setStroke(new BasicStroke(3));
        for(ProjectNodesLinkDraw.Edge edge:dr.edges)  {
            if((edge.ballin==ballin && edge.ballfin==ballfin) || (edge.ballfin==ballin && edge.ballin==ballfin)) {
                g.setColor(Color.blue);
            }
            else 
                g.setColor(Color.GREEN);
            g2.drawLine(edge.x1, edge.y1,edge.x2 , edge.y2);
        }
    }

    private void init() {
        this.setPreferredSize(new Dimension(1080, 720));
        main=new JFrame();
        main.setSize(1580, 720);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
        main.setLayout(new BorderLayout());
        main.getContentPane().add(this,BorderLayout.WEST);
    }

    private void setUp() {
        graph= new int[dr.balls.size()+1][dr.balls.size()+1];
        dist= new int[V];
        
        for(ProjectNodesLinkDraw.Edge x:dr.edges) {
            graph[x.ballin][x.ballfin]=x.cost;
            graph[x.ballfin][x.ballin]=x.cost;
        }
        
        for(int i=1;i<graph.length;i++) {
            for(int j=1;j<graph[i].length;j++) {
                if(i==j) {
                    graph[i][j]=0;
                    continue;
                }
                if(graph[i][j]==0) {
                    graph[i][j]=Integer.MAX_VALUE;
                }
            }
        }
    }
    
    int minDistance(int dist[], Boolean sptSet[]) 
    { 
        int min = Integer.MAX_VALUE, min_index = -1; 
  
        for (int v = 1; v < V; v++) 
            if (sptSet[v] == false && dist[v] <= min) { 
                min = dist[v]; 
                min_index = v; 
            } 
  
        return min_index; 
    }
    
    void dijkstra(int graph[][], int src) throws InterruptedException 
    { 
        source=src;
  
        for (int i = 1; i < V; i++) { 
            dist[i] = Integer.MAX_VALUE; 
            sptSet[i] = false; 
        } 
        dist[src] = 0; 
        for (int count = 1; count < V ; count++) { 
            int u = minDistance(dist, sptSet); 
            sptSet[u] = true; 
            repaint();
            Thread.sleep(1000);
            for (int v = 1; v < V; v++) {
                if(graph[u][v]!= Integer.MAX_VALUE && graph[u][v]!=0 &&!sptSet[v]) {
                    ballin=u;
                    ballfin=v;
                    partialy=v;
                }
                else {
                    ballin=-1;
                    ballfin=-1;
                    partialy=-1;
                    continue;
                }
                repaint();
                Thread.sleep(4000);
                if (!sptSet[v] && graph[u][v] != Integer.MAX_VALUE && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                   dist[v] = dist[u] + graph[u][v];
                } 
            } 
        } 
        ballfin=-1;
        ballin=-1;
        repaint();
    }

    @Override
    public void run() {
        try {
            dijkstra(graph, 1);
        } catch (InterruptedException ex) {
            Logger.getLogger(FrmDikstra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
