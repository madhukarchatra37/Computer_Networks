public class Entity2 extends Entity {
	// Perform any necessary initialization in the constructor
	int mincost[];
	public Entity2() {
		System.out.println("Constructor of Node 2 is called");
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				distanceTable[i][j] = 999;
		}
		distanceTable[0][0] = 3;
		distanceTable[1][1] = 1;
		distanceTable[2][2] = 0;
		distanceTable[3][3] = 2;
		
		int[] distanceVector = {3,1,0,2};

	      for(int i = 0; i < distanceTable.length; i++){
	        if(i != 2){
	          Packet p = new Packet(2, i, distanceVector);
	          NetworkSimulator.toLayer2(p);
	        }
	      }
		printDT();
		
	}

	// Handle updates when a packet is received. Students will need to call
	// NetworkSimulator.toLayer2() with new packets based upon what they
	// send to update. Be careful to construct the source and destination of
	// the packet correctly. Read the warning in NetworkSimulator.java for more
	// details.
	public void update(Packet p) {
		System.out.println("The update method is called by "+"source "+p.getSource()+" to destination "+p.getDest());
		
		 boolean updated = false;

	      int[] distanceVector = new int[distanceTable.length];

	      for(int i = 0; i < distanceTable.length; i++){
	        int t1 = Math.min(distanceTable[i][0], distanceTable[i][1]);
	        int t2 = Math.min(distanceTable[i][2], distanceTable[i][3]);
	        distanceVector[i] = Math.min(t1, t2);
	      }

	      for(int i = 0; i < p.mincost.length; i++){
	        if(p.getMincost(i)+distanceTable[p.getSource()][p.getSource()] < distanceTable[i][p.getSource()]){
	          distanceTable[i][p.getSource()] = p.getMincost(i)+distanceTable[p.getSource()][p.getSource()];
	          if(distanceTable[i][p.getSource()]<distanceVector[i]){
	            distanceVector[i] = distanceTable[i][p.getSource()];
	            updated = true;
	          }
	        }

	      }
	      if(updated){
	    	  System.out.println("The distance table of Node 2 is updated");
	          for(int i =0; i < distanceTable.length; i++){
	            if(i != 2){
	              Packet newPacket = new Packet(2, i, distanceVector);
	              NetworkSimulator.toLayer2(newPacket);
	            }
	          }
	          printDT();
	      }
	      else{
		      printDT();}

	}

	public void linkCostChangeHandler(int whichLink, int newCost) {
	}

	public void printDT() {
		System.out.println();
		System.out.println("           via");
		System.out.println(" D2 |   0   1   3");
		System.out.println("----+------------");
		for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
			if (i == 2) {
				continue;
			}

			System.out.print("   " + i + "|");
			for (int j = 0; j < NetworkSimulator.NUMENTITIES; j++) {
				if (j == 2) {
					continue;
				}

				if (distanceTable[i][j] < 10) {
					System.out.print("   ");
				} else if (distanceTable[i][j] < 100) {
					System.out.print("  ");
				} else {
					System.out.print(" ");
				}

				System.out.print(distanceTable[i][j]);
			}
			System.out.println();
		}
	}
}
