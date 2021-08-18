import java.util.ArrayList;

public class Path_ADV {

    private int x, y;
    private boolean terminated;
    private int value;
    private ArrayList<ArrayList<Integer>> prev;


    public Path_ADV(int x, int y, int value){
        terminated=false;
        this.x=x;
        this.y=y;
        this.value=value;
        this.prev=new ArrayList<>();
        addLocation(0,0);
    }
    public Path_ADV(int x, int y, int value, ArrayList<ArrayList<Integer>> previous){
        terminated=false;
        this.x=x;
        this.y=y;
        this.value=value;
        this.prev = new ArrayList<>();
        this.prev.addAll(previous);
    }

    public void move(int newX, int newY){
        x=newX;
        y=newY;
    }

    public void update(ArrayList<ArrayList<Integer>> board){
        value+= board.get(y).get(x);
    }

    public void termCheck(int maxValue, int move, ArrayList<Path> paths){


    }
    public void terminate(){
        terminated=true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<ArrayList<Integer>> getPrev() {
        return prev;
    }

    public void addLocation(int x, int y) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(x);
        temp.add(y);
        this.prev.add(temp);
    }
    public ArrayList<ArrayList<Integer>> giveNewLocation(int x, int y) {
        ArrayList<ArrayList<Integer>> updated = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        updated.addAll(this.prev);
        temp.add(x);
        temp.add(y);
        updated.add(temp);
        return updated;
    }
}
