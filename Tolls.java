import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Tolls {

    /*

    Created by: Joshua Cutting
    Objective: Takes the input of a given maze (currently configured to a 10x10 maze) and finds the fastest pathway.
               The maze lacks walls but each space has a value. When you step into a space, that pathway gains the value.
               The goal is to find the pathway that takes the least amount of points to get to the objective.
               The output gives the least amount of points it takes to get to the objective as well as the list of coorindates
               outlining the best path.


    int x will equal spaces from the left starting at 0 from the arraylist
    int y will equal spaces from the top starting at 0 from the arraylist
    (0,0) is the top left aka A1
    (9,9) is the bottom right aka J10 aka THE OBJECTIVE
    stops is the actual maze board
    paths is a list of ALL current running pathways
    newpaths is used to track all unmerged/unvetted paths that may become actual paths
    bestNewPaths is the list used to contain all vetted/merged paths that DO become paths.
    map is a hashmap used to convert between coordinates and the list of locations in newpaths that those paths reside
     */

    static ArrayList<ArrayList<Integer>> stops = new ArrayList<>();
    static int destX=9;
    static int destY=9;
    static int startX=0;
    static int startY=0;
    static int startValue=0;

    public static void main(String[] args){


        Scanner scan = new Scanner(System.in);
        ArrayList<Path_ADV> paths = new ArrayList<>();
        ArrayList<Path_ADV> newpaths = new ArrayList<>();
        ArrayList<Path_ADV> winners = new ArrayList<>();
        ArrayList<Path_ADV> bestNewPaths = new ArrayList<>();
        HashMap<ArrayList<Integer>, ArrayList<Integer>> map = new HashMap<>();

        // start of getting user input
        for(int i = startY; i<=destY; i++){
            stops.add(new ArrayList<>());
            String line = scan.nextLine();
            String[] list = line.split(" ");
            for(String field : list){
                stops.get(i).add(Integer.parseInt(field));
            }
        }
        // end of getting input from the user


        // the original path at the starting destination
        paths.add(new Path_ADV(startX,startY,startValue));

        while(paths.size()!=0){

           // System.out.println("Move #"+moves+" with "+paths.size()+" active paths.");

            // start of movement decision making system
            /*
                Explanation: The original path will recognize where it can move. It then will create new paths at each
                             possible "new" location. It will NOT save itself. It will only create new paths in the new
                             locations. Paths cannot move into a space if it has already been there.
                             All new paths are stored in the ArrayList newpaths.
                             All newpaths are vetted and merged. If there is more than one path in one coordinate,
                             they will compete for the space. The path with a lesser "value" at that point
                             will be kept and all other eliminated. It is redundant to keep extra paths at the same
                             location.
                             All previous paths are flushed and replaced by the vetted/merged paths
                             The process repeats until all paths have either finished in the destination, can no longer move,
                             or are merged.
                             Due to the merge strategy, there will never be more than 100 pathways at one given turn.
                             NOTE: all pathways will move at the same time. There will never be a path with more turns taken
                                   than another.
             */
            for(int i = 0; i<paths.size(); i++){

                // evals which direction it can go
                // also checks where it has ALREADY been

                // left
                if(canMoveHorizontal(paths.get(i).getX()-1, paths.get(i).getY(), paths.get(i).getPrev())){
                    if(paths.get(i).getX()-1 == destX && paths.get(i).getY() == destY){
                        winners.add(new Path_ADV(paths.get(i).getX()-1, paths.get(i).getY(), paths.get(i).getValue()+stops.get(paths.get(i).getY()).get(paths.get(i).getX()-1), paths.get(i).giveNewLocation(paths.get(i).getX()-1, paths.get(i).getY())));
                    }else{
                        newpaths.add(new Path_ADV(paths.get(i).getX()-1, paths.get(i).getY(), paths.get(i).getValue()+stops.get(paths.get(i).getY()).get(paths.get(i).getX()-1), paths.get(i).giveNewLocation(paths.get(i).getX()-1, paths.get(i).getY())));
                    }
                }
                // right
                if(canMoveHorizontal(paths.get(i).getX()+1, paths.get(i).getY(), paths.get(i).getPrev())){

                    if(paths.get(i).getX()+1 == destX && paths.get(i).getY() == destY){

                        winners.add(new Path_ADV(paths.get(i).getX()+1, paths.get(i).getY(), paths.get(i).getValue()+stops.get(paths.get(i).getY()).get(paths.get(i).getX()+1), paths.get(i).giveNewLocation(paths.get(i).getX()+1, paths.get(i).getY())));
                    }else{
                        newpaths.add(new Path_ADV(paths.get(i).getX()+1, paths.get(i).getY(), paths.get(i).getValue()+stops.get(paths.get(i).getY()).get(paths.get(i).getX()+1), paths.get(i).giveNewLocation(paths.get(i).getX()+1, paths.get(i).getY())));
                    }

                }
                // down
                if(canMoveVertical(paths.get(i).getX(), paths.get(i).getY()+1, paths.get(i).getPrev())){

                    if(paths.get(i).getX() == destX && paths.get(i).getY()+1 == destY){

                        winners.add(new Path_ADV(paths.get(i).getX(), paths.get(i).getY()+1, paths.get(i).getValue()+stops.get(paths.get(i).getY()+1).get(paths.get(i).getX()), paths.get(i).giveNewLocation(paths.get(i).getX(), paths.get(i).getY()+1)));
                    }else{
                        newpaths.add(new Path_ADV(paths.get(i).getX(), paths.get(i).getY()+1, paths.get(i).getValue()+stops.get(paths.get(i).getY()+1).get(paths.get(i).getX()), paths.get(i).giveNewLocation(paths.get(i).getX(), paths.get(i).getY()+1)));
                    }
                }
                // up
                if(canMoveVertical(paths.get(i).getX(), paths.get(i).getY()-1, paths.get(i).getPrev())){
                    if(paths.get(i).getX() == destX && paths.get(i).getY()-1 == destY){
                        winners.add(new Path_ADV(paths.get(i).getX(), paths.get(i).getY()-1, paths.get(i).getValue()+stops.get(paths.get(i).getY()-1).get(paths.get(i).getX()), paths.get(i).giveNewLocation(paths.get(i).getX(), paths.get(i).getY()-1)));
                    }else{
                        newpaths.add(new Path_ADV(paths.get(i).getX(), paths.get(i).getY()-1, paths.get(i).getValue()+stops.get(paths.get(i).getY()-1).get(paths.get(i).getX()), paths.get(i).giveNewLocation(paths.get(i).getX(), paths.get(i).getY()-1)));
                    }

                }

            }

            // here we are going to eliminate all paths that are sharing a space. if they are sharing a space... they are redundant. Keep ONLY the best one
            // this means AT MOST... there will only be a total of 100 active paths
            // this should increase efficiency INCREDIBLY
            bestNewPaths.clear();
            map.clear();
            // configures the map with all locations in newpaths
            // map is arraylist<int> to arraylist<int>
            //        coordinate     to list of locations in newpaths

            for(int i = 0; i<newpaths.size(); i++){
                map.put(newpaths.get(i).getPrev().get(newpaths.get(i).getPrev().size()-1), new ArrayList<>());
            }

            // this forloop essentially creates the map between each used coorindate and ALL paths in that coordinate
            // this is so we can later compare each set of paths and selectively pick out the best
            // its kinda like darwinism for this program

            for(int i = 0; i<newpaths.size(); i++){
                // temp is the current list of spaces AT the current coordinate
                ArrayList<Integer> temp = new ArrayList<>(map.get(newpaths.get(i).getPrev().get(newpaths.get(i).getPrev().size()-1)));
                temp.add(i);
                map.put(newpaths.get(i).getPrev().get(newpaths.get(i).getPrev().size()-1), temp);
            }

            // the following loop now actually does the picking of best paths based on the least value
            // it does account for paths of equal value just in case :D

            ArrayList<Path_ADV> lowestValue =new ArrayList<>();

            for(ArrayList<Integer> key : map.keySet()){
                lowestValue.clear();
                lowestValue.add(new Path_ADV(0,0,Integer.MAX_VALUE));
                for(Integer spot : map.get(key)){
                    if(lowestValue.get(0).getValue() > newpaths.get(spot).getValue()){
                        lowestValue.clear();
                        lowestValue.add(newpaths.get(spot));
                    }else if(lowestValue.get(0).getValue() == newpaths.get(spot).getValue()){
                        lowestValue.add(newpaths.get(spot));
                    }
                }
                bestNewPaths.addAll(lowestValue);


            }



            // flush of paths and then adds the new "merged/vetted" paths

            paths.clear();

            paths.addAll(bestNewPaths);
            newpaths.clear();

            // end of the while loop here
            // this process repeats until all paths have completed their turns


        }
        // finds the best path of all successful paths
        ArrayList<Path_ADV> smallest=new ArrayList<>();
        smallest.add(new Path_ADV(0,0,Integer.MAX_VALUE));
        for(Path_ADV win : winners){
          //  System.out.println("Value: "+win.getValue()+" with path: "+win.getPrev());

            if(smallest.get(0).getValue()>win.getValue()){
                smallest.clear();
                smallest.add(win);
            }else if(smallest.get(0).getValue()==win.getValue()){

                smallest.add(win);
            }
        }
        if(winners.size()!=0){
            System.out.println("Best Value: "+smallest.get(0).getValue());
            for(Path_ADV small : smallest){
                System.out.println("Best Path: "+small.getPrev());
            }
        }else{
            System.out.println("No valid winners.");
        }






    }


    // these functions are used to determine if moving is allowed
    // also prevents out of index errors :D

    public static boolean canMoveHorizontal(int x, int y, ArrayList<ArrayList<Integer>> previousJumps){
        if(x>=0 && x<stops.get(0).size()){
            boolean oldspace=false;
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(x);
            temp.add(y);
            for(ArrayList<Integer> jump : previousJumps){
                if(temp.equals(jump)){
                    oldspace=true;
                }
            }
            return !oldspace;
        }
        return false;
    }
    public static boolean canMoveVertical(int x, int y, ArrayList<ArrayList<Integer>> previousJumps){
        if(y>=0 && y<stops.size()){
            boolean oldspace=false;
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(x);
            temp.add(y);
            for(ArrayList<Integer> jump : previousJumps){
                if(temp.equals(jump)){
                    oldspace=true;
                }
            }
            return !oldspace;
        }
        return false;
    }



}
