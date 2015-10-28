import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class mancala {

    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException{

        BufferedReader br = null;
        try{

            int task, player, cutOffDepth, numberOfStonesInP1Mancala, numberOfStonesInP2Mancala;
            String boardStateP1,boardStateP2;


            String currentLine;
            List<String> input=new ArrayList<String>();
            br = new BufferedReader(new FileReader(args[0]));

            while ((currentLine = br.readLine()) != null) {
                input.add(currentLine);
            }

            task=Integer.parseInt(input.get(0));
            player=Integer.parseInt(input.get(1));
            cutOffDepth=Integer.parseInt(input.get(2));
            boardStateP2=input.get(3);
            boardStateP1=input.get(4);
            numberOfStonesInP2Mancala=Integer.parseInt(input.get(5));
            numberOfStonesInP1Mancala=Integer.parseInt(input.get(6));

            String split1[]=new String[10];
            split1= boardStateP1.split("\\s");

            int[] p1State= new int[split1.length];

            String split2[]=new String[10];
            split2= boardStateP2.split("\\s");

            int[] p2State= new int[split2.length];

            for(int i=0;i<split1.length;i++){
                p1State[i]=Integer.parseInt(split1[i]);
                p2State[i]=Integer.parseInt(split2[i]);
            }


            if(task == 1){
            	Greedy greedyObj=new Greedy(task, player, 0, cutOffDepth, p2State, p1State, numberOfStonesInP2Mancala, numberOfStonesInP1Mancala);
            	greedyObj.greedy();
            }

            if(task == 2){
                Minimax minimaxObj=new Minimax(task, player, 0, cutOffDepth, p2State, p1State, numberOfStonesInP2Mancala, numberOfStonesInP1Mancala);
                minimaxObj.minimax();
            }

            if(task == 3){
                Alphabeta alphaBetaObj=new Alphabeta(task, player, 0, cutOffDepth, p2State, p1State, numberOfStonesInP2Mancala, numberOfStonesInP1Mancala, Integer.MIN_VALUE,Integer.MAX_VALUE);
                alphaBetaObj.alphaBeta();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                if (br != null)br.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
