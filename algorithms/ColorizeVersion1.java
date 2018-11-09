package algorithms;
import graph.*;
import log.*;
import java.util.*; 
import java.io.*;

public class ColorizeVersion1 {
    
    /**
     * 
     */
    protected ArrayList<Vertex> vertexList;
    
    /**
     * 
     */
    protected Queue<Integer> colors;
    
    /**
     * 
     */
    protected WriteLogFile log;
    
    /**
     * 
     */
    protected PrintWriter pw;
    
    /**
     * [StringBuilder description]
     * @return [description]
     */
    protected StringBuilder sb = new StringBuilder();

    public ColorizeVersion1 () throws IOException {

        colors = new LinkedList<Integer>();

    }

    /**
     * [V1 description]
     * @param  graph                 [description]
     * @return                       [description]
     * @throws IOException           [description]
     */
    public ColorizeVersion1 (Graph graph) throws IOException {

        log = new WriteLogFile("output/" + graph.getId() + "/log_V1.txt");

        pw = new PrintWriter(new File("output/" + graph.getId() + "/id_color_V1.csv"));
        
        colors = new LinkedList<Integer>();

        init(graph);

    }

    protected void init (Graph graph) {

        vertexList = graph.getVertexList();
        
        setColorVertex(vertexList.get(0));
        
        for (int j = 1; j < vertexList.size(); j++) {
        
            saveNeighborColors(j);
        
            setColorVertex(vertexList.get(j));
        
        }
        
        sb.append("id,Color\n");
        
        for (int p = 0; p < graph.getOrder(); p++)
        
            sb.append((vertexList.get(p).getId()+1) + "," + (vertexList.get(p).getColor()) + "\n");
        
        pw.write(sb.toString());
        
        pw.close();
        
        log.close();

    }

    /**
     * [setColorVertex description]
     * @param vertex [description]
     */
    protected void setColorVertex (Vertex vertex) {

        log.write("Get", "first color that is available for vertex", Integer.toString(vertex.getId()+1));        

        int color = getFirstColorAvailable();

        log.writeln(Integer.toString(color));

        log.writef("Set", "in " + color + " the color of vertex ", Integer.toString(vertex.getId()+1), "");        
    
        vertex.setColor(color);
    
    }

    /**
     * [saveNeighborColors description]
     * @param j [description]
     */
    protected void saveNeighborColors (int j) {
    
        colors = new LinkedList<Integer>();
    
        log.writef("Clear", "queue of colors");        

        for (int i = 0; i < j; i++) {

            log.write("Check", "if vertex " + Integer.toString(vertexList.get(j).getId()+1) + " is neighbor of vertex", Integer.toString(vertexList.get(i).getId()+1));        
            
            if (vertexList.get(j).isNeighbor(vertexList.get(i).getId())) {

                log.writeln("Yes");

                log.writef("Add", Integer.toString(vertexList.get(i).getColor()) + " to queue of colors");        

                colors.add(vertexList.get(i).getColor());

            } else

                log.writeln("No");

        }
    
    }

    /**
     * [getFirstColorAvailable description]
     * @return [description]
     */
    protected int getFirstColorAvailable () {
    
        int k = 1;

        while(colors.contains(k))
    
            k++;
    
        return k;
    
    }

}