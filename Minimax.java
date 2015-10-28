import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;


public class Minimax implements Cloneable{

        public static int globalDepth;
        public static int globalPlayer;
        public static int globalPosition;

        public int task;
        public int player;
        public int cutOffDepth;
        public int depth;
        public int[]p2State;
        public int[]p1State;
        public int p2Mancala;
        public int p1Mancala;
        public int value;
        public int checkIfExtraChance;
        public String name; 
        public int index;
        public boolean endGame;

		public Minimax(int player, int depth, int[] p2State, int[] p1State, int p2Mancala, int p1Mancala, int value, String name, int index) {
            super();
            this.player = player;
            this.depth = depth;
            this.p2State = p2State;
            this.p1State = p1State;
            this.p2Mancala = p2Mancala;
            this.p1Mancala = p1Mancala;
            this.value=value;
            this.name=name;
            this.index=index;
        }

        public Minimax(int task, int player,int depth, int cutOffDepth, int[] p2State, int[] p1State, int p2Mancala, int p1Mancala) {
            super();
            this.task = task;
            this.player = player;
            this.depth = depth;
            this.cutOffDepth=cutOffDepth;
            this.p2State = p2State;
            this.p1State = p1State;
            this.p2Mancala = p2Mancala;
            this.p1Mancala = p1Mancala;
        }

        public Minimax() {
        }
        
        
        public boolean isEndGame() {
			return endGame;
		}

		public void setEndGame(boolean endGame) {
			this.endGame = endGame;
		}

		public int getTask() {
			return task;
		}

		public void setTask(int task) {
			this.task = task;
		}

		public int getPlayer() {
			return player;
		}

		public void setPlayer(int player) {
			this.player = player;
		}

		public int getCutOffDepth() {
			return cutOffDepth;
		}

		public void setCutOffDepth(int cutOffDepth) {
			this.cutOffDepth = cutOffDepth;
		}

		public int getDepth() {
			return depth;
		}

		public void setDepth(int depth) {
			this.depth = depth;
		}

		public int[] getP2State() {
			return p2State;
		}

		public void setP2State(int[] p2State) {
			this.p2State = p2State;
		}

		public int[] getP1State() {
			return p1State;
		}

		public void setP1State(int[] p1State) {
			this.p1State = p1State;
		}

		public int getP2Mancala() {
			return p2Mancala;
		}

		public void setP2Mancala(int p2Mancala) {
			this.p2Mancala = p2Mancala;
		}

		public int getP1Mancala() {
			return p1Mancala;
		}

		public void setP1Mancala(int p1Mancala) {
			this.p1Mancala = p1Mancala;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public int getCheckIfExtraChance() {
			return checkIfExtraChance;
		}

		public void setCheckIfExtraChance(int checkIfExtraChance) {
			this.checkIfExtraChance = checkIfExtraChance;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

        @Override
		public String toString() {
			return "Minimax [player=" + player + ", depth=" + depth
					+ ", p2State=" + Arrays.toString(p2State) + ", p1State="
					+ Arrays.toString(p1State) + ", p2Mancala=" + p2Mancala
					+ ", p1Mancala=" + p1Mancala + ", value=" + value
					+ ", checkIfExtraChance=" + checkIfExtraChance + ", name="
					+ name + ", index=" + index + "]";
		}

		@Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
        public void minimax() throws CloneNotSupportedException, FileNotFoundException {
        	
        	PrintStream nextState = new PrintStream(new FileOutputStream("next_state.txt"));
        	PrintStream traverseLog = new PrintStream(new FileOutputStream("traverse_log.txt"));

        	System.setOut(traverseLog);
        	
            globalDepth=cutOffDepth;
            globalPlayer=this.player;
            
            System.out.println("Node,Depth,Value");
            
            Minimax finalObj = (Minimax) this.clone();
            maxValue(finalObj);
            traverseLog.close();

            int checkIfEndedInMancala=playMancala(finalObj,globalPosition, globalPlayer);
            while(checkIfEndedInMancala == 1){
                maxValue(finalObj);
                checkIfEndedInMancala = playMancala(finalObj,globalPosition, globalPlayer);
            }
            System.setOut(nextState);
            finalObj.printNextState();
            nextState.close();
        }

        public int maxValue(Minimax currentState) throws CloneNotSupportedException {
            if(currentState.getDepth() == globalDepth ){
            	return currentState.eval();
            }
            
             currentState.setValue(Integer.MIN_VALUE);
             
             printTraverseLog(currentState);
            
             if(currentState.getPlayer() == 1){
                // for each in legal actions
                for(int j=0;j < currentState.p1State.length;j++){
                    if(currentState.p1State[j] == 0)  continue;

                    int p2Arr[]=currentState.getP2State().clone();
                    int p1Arr[]=currentState.getP1State().clone();

                    Minimax newObj=new Minimax(2, (currentState.getDepth()+1), p2Arr, p1Arr, currentState.p2Mancala, currentState.p1Mancala, currentState.getValue(), "B", (j+2));

                    int checkIfEndedInMancala=playMancala(newObj, j, 1);
                    
                      int newValue;
                      
                    if(newObj.isEndGame()){
                    	if(checkIfEndedInMancala == 1){
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"-Infinity");
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    	}
                    	else{
                    		if(newObj.getDepth() == globalDepth){
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    		else{
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"Infinity");
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    	}
                    	newValue = currentState.getEval(newObj);
                  }
                    else{

                    	if(checkIfEndedInMancala == 1){
                    		newObj.setPlayer(1);
                    		newObj.setDepth((newObj.getDepth()-1));
                    		newObj.setCheckIfExtraChance(1);
                    		newValue = maxValue(newObj);
                    	}
                    	else{
                    		newValue = minValue(newObj);
                    	}
                  }
                    if(currentState.getValue() < newValue){
                    	if(currentState.getCheckIfExtraChance() != 1 ){
                    		if(currentState.getDepth() == 0){
                    			globalPosition = j;
                    		}
                    	}
                    	else{
                    		if(currentState.getDepth()+1 == 0){
                    			globalPosition = j;
                    		}
                    	}
                        currentState.setValue(newValue);
                    }
                    printTraverseLog(currentState);
                }
                return currentState.getValue();
            }
            else{
                // for each in legal actions
                for(int j=0;j < currentState.getP2State().length;j++){
                    if(currentState.getP2State()[j] == 0)  continue;

                    int p2Arr[]=currentState.getP2State().clone();
                    int p1Arr[]=currentState.getP1State().clone();

                    Minimax newObj=new Minimax(1, (currentState.getDepth()+1), p2Arr, p1Arr, currentState.p2Mancala, currentState.p1Mancala, currentState.getValue(), "A", (j+2));

                    int checkIfEndedInMancala=playMancala(newObj,j, 2);
                    
                    int newValue;
                    
                    if(newObj.isEndGame()){
                    	if(checkIfEndedInMancala == 1){
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"-Infinity");
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    	}
                    	else{
                    		if(newObj.getDepth() == globalDepth){
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    		else{
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"Infinity");
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    	}
                    	newValue = currentState.getEval(newObj);
                  }
                    else{
                    	if(checkIfEndedInMancala == 1){
                    		newObj.setPlayer(2);
                    		newObj.setDepth((newObj.getDepth()-1));
                    		newObj.setCheckIfExtraChance(1);
                    		newValue = maxValue(newObj);
                    	}
                    	else{
                    		newValue = minValue(newObj);
                    	}
                  }
                    
                    if(currentState.getValue() < newValue){
                    	if(currentState.getCheckIfExtraChance() != 1 ){
                    		if(currentState.getDepth() == 0){
                    			globalPosition = j;
                    		}
                    	}
                    	else{
                    		if(currentState.getDepth()+1 == 0){
                    			globalPosition = j;
                    		}
                    	}
                        currentState.setValue(newValue);
                    }
                    printTraverseLog(currentState);
                }
                return currentState.getValue();
            }
        }

        public int minValue(Minimax currentState) throws CloneNotSupportedException {

            //base condition
            if(currentState.getDepth() == globalDepth ){
            	return currentState.eval();
            }

            currentState.setValue(Integer.MAX_VALUE);
            
            printTraverseLog(currentState);

            if(currentState.getPlayer() == 1){
            	
                // for each in legal actions
                for(int j=0;j < currentState.getP1State().length;j++){
                    if(currentState.getP1State()[j] == 0)  continue;

                    int p2Arr[]=currentState.getP2State().clone();
                    int p1Arr[]=currentState.getP1State().clone();
                    
                    
                    Minimax newObj=new Minimax(2, (currentState.getDepth()+1), p2Arr, p1Arr, currentState.p2Mancala, currentState.p1Mancala, currentState.getValue(), "B", (j+2));
                   
                    int checkIfEndedInMancala=playMancala(newObj, j, 1);
                   

                    int newValue;
                    
                    if(newObj.isEndGame()){
                    	if(checkIfEndedInMancala == 1){
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"Infinity");
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    	}
                    	else{
                    		if(newObj.getDepth() == globalDepth){
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    		else{
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"-Infinity");
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    	}
                    	newValue = currentState.getEval(newObj);
                  }
                    else{
                    	if(checkIfEndedInMancala == 1){
                        	newObj.setPlayer(1);
                        	newObj.setDepth((newObj.getDepth()-1));
                            newObj.setCheckIfExtraChance(1);
                        	newValue = minValue(newObj);
                        }
                        else{
                            newValue = maxValue(newObj);
                        }
                  }
                    if(currentState.getValue() > newValue) {
                    	currentState.setValue(newValue);
                    }
                    
                    printTraverseLog(currentState);
                }
                return currentState.getValue();
            }
            else{
            	
                // for each in legal actions
                for(int j=0;j < currentState.getP2State().length;j++){
                    if(currentState.getP2State()[j] == 0)  continue;

                    int p2Arr[]=currentState.getP2State().clone();
                    int p1Arr[]=currentState.getP1State().clone();

                    Minimax newObj=new Minimax(1, (currentState.getDepth()+1), p2Arr, p1Arr, currentState.p2Mancala, currentState.p1Mancala, currentState.getValue(), "A", (j+2));
                    
                    int checkIfEndedInMancala=playMancala(newObj,j, 2);
                    

                    int newValue;
                    if(newObj.isEndGame()){
                    	if(checkIfEndedInMancala == 1){
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"Infinity");
                    		System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    	}
                    	else{
                    		if(newObj.getDepth() == globalDepth){
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    		else{
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+"-Infinity");
                    			System.out.println(newObj.getName()+newObj.getIndex()+","+newObj.getDepth()+","+getEval(newObj));
                    		}
                    	}
                    	newValue = currentState.getEval(newObj);
                  }
                    else{
                    	if(checkIfEndedInMancala == 1){
                        	newObj.setPlayer(2);
                        	newObj.setDepth((newObj.getDepth()-1));
                            newObj.setCheckIfExtraChance(1);
                        	newValue = minValue(newObj);
                        }
                        else{
                            newValue = maxValue(newObj);
                        }
                  }
                    if(currentState.getValue() > newValue) {
                    	currentState.setValue(newValue);
                    }
                    printTraverseLog(currentState);
                }
                return currentState.getValue();
            }
        }

        public int eval(){
        	
            if(globalPlayer == 1){
            	if(this.getCheckIfExtraChance() == 0){
            		System.out.println(this.getName()+this.getIndex()+","+this.getDepth()+","+(this.p1Mancala-this.p2Mancala));
            	}
        		return this.p1Mancala-this.p2Mancala;
            }
            else{
            	if(this.getCheckIfExtraChance() == 0){
            		System.out.println(this.getName()+this.getIndex()+","+this.getDepth()+","+(this.p2Mancala-this.p1Mancala));
               	}
            	return this.p2Mancala-this.p1Mancala;
            }
        }

        public int playMancala(Minimax currentObj,int pit, int player){
            int endedInMancala=0;
            if(player == 1){
                int numberOfStonesToBeDistributed=currentObj.p1State[pit];
                currentObj.p1State[pit]=0;
                while(numberOfStonesToBeDistributed > 0){

                    for(int i=pit+1; i<currentObj.p1State.length && numberOfStonesToBeDistributed > 0;i++){
                        if(numberOfStonesToBeDistributed == 1 && currentObj.p1State[i] == 0){
                            currentObj.p1Mancala+=1+currentObj.p2State[i];
                            numberOfStonesToBeDistributed--;
                            currentObj.p2State[i]=0;
                            break;
                        }
                        currentObj.p1State[i]++;
                        numberOfStonesToBeDistributed--;
                    }

                    if(numberOfStonesToBeDistributed > 0){
                        currentObj.p1Mancala++;
                        if(numberOfStonesToBeDistributed == 1) endedInMancala=1;
                        numberOfStonesToBeDistributed--;
                    }

                    for(int j=currentObj.p2State.length-1;j>=0 && numberOfStonesToBeDistributed > 0;j--){
                        currentObj.p2State[j]++;
                        numberOfStonesToBeDistributed--;
                    }

                    for(int k=0;k<=pit && numberOfStonesToBeDistributed > 0;k++){
                    	if(numberOfStonesToBeDistributed == 1 && currentObj.p1State[k] == 0){
                            currentObj.p1Mancala+=1+currentObj.p2State[k];
                            numberOfStonesToBeDistributed--;
                            currentObj.p2State[k]=0;
                            break;
                        }
                        currentObj.p1State[k]++;
                        numberOfStonesToBeDistributed--;
                    }
                }
            }
            else{
                int numberOfStonesToBeDistributed=currentObj.p2State[pit];
                currentObj.p2State[pit]=0;
                while(numberOfStonesToBeDistributed > 0){

                    for(int i=pit-1;i>=0 && numberOfStonesToBeDistributed > 0;i--){
                        if(numberOfStonesToBeDistributed == 1 && currentObj.p2State[i] == 0){
                            currentObj.p2Mancala+=1+currentObj.p1State[i];
                            numberOfStonesToBeDistributed--;
                            currentObj.p1State[i]=0;
                            break;
                        }
                        currentObj.p2State[i]++;
                        numberOfStonesToBeDistributed--;
                    }

                    if(numberOfStonesToBeDistributed > 0){
                        currentObj.p2Mancala++;
                        if(numberOfStonesToBeDistributed == 1) endedInMancala=1;
                        numberOfStonesToBeDistributed--;
                    }

                    for(int j=0;j<currentObj.p1State.length && numberOfStonesToBeDistributed > 0;j++){
                        currentObj.p1State[j]++;
                        numberOfStonesToBeDistributed--;
                    }

                    for(int k=currentObj.p2State.length-1;k>=pit && numberOfStonesToBeDistributed > 0;k--){
                    	if(numberOfStonesToBeDistributed == 1 && currentObj.p2State[k] == 0){
                            currentObj.p2Mancala+=1+currentObj.p1State[k];
                            numberOfStonesToBeDistributed--;
                            currentObj.p1State[k]=0;
                            break;
                        }
                        currentObj.p2State[k]++;
                        numberOfStonesToBeDistributed--;
                    }
                }
            }
            
            int []sum=new int[2];
            for(int m=0;m<currentObj.p1State.length;m++){
            	sum[0]+=currentObj.p1State[m];
            	sum[1]+=currentObj.p2State[m];
            }
            if(sum[0] == 0){ 
            	currentObj.p2Mancala+=sum[1];
            	currentObj.p2State=new int[currentObj.p2State.length];
            	currentObj.setEndGame(true);
            }
            if(sum[1] == 0){ 
            	currentObj.p1Mancala+=sum[0];
            	currentObj.p1State=new int[currentObj.p2State.length];
            	currentObj.setEndGame(true);
            }
            return endedInMancala;
        }
        
        public void printNextState(){
    	    for(int str:this.getP2State())	System.out.print(str+" ");
    	    System.out.println();
    	    for(int str1:this.getP1State())	System.out.print(str1+" ");
    	    System.out.println();
    	    System.out.println(this.getP2Mancala());
    	    System.out.println(this.getP1Mancala());
        }
        
        public void printTraverseLog(Minimax currentState){
        	if(currentState.getDepth() > 0){
            	if(currentState.getCheckIfExtraChance() == 1){
            		System.out.println(currentState.getName()+currentState.getIndex()+","+(currentState.getDepth()+1)+","+getValue(currentState.getValue()));
            	}
            	else
            		System.out.println(currentState.getName()+currentState.getIndex()+","+currentState.getDepth()+","+getValue(currentState.getValue()));
            }
            else{
            	if(currentState.getCheckIfExtraChance() == 1){
            		System.out.println(currentState.getName()+currentState.getIndex()+","+(currentState.getDepth()+1)+","+getValue(currentState.getValue()));
            	}
            	else
            		System.out.println("root"+","+currentState.getDepth()+","+getValue(currentState.getValue()));
            }
        }
        
        public String getValue(int x){
        	if(x == Integer.MAX_VALUE){
        		return "Infinity";
        	}
        	else if(x==Integer.MIN_VALUE){
        		return "-Infinity";
        	}
        	else{
        		return Integer.toString(x);
        	}
        }
        
        public int getEval(Minimax obj){
        	if(globalPlayer == 1) return obj.getP1Mancala()-obj.getP2Mancala();
        	else
        		return obj.getP2Mancala()-obj.getP1Mancala();
        }
    }