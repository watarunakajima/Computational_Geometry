import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class GrahamScan extends JPanel{
    public static void main(String[] args){
        JFrame frame = new JFrame();
        JPanel app= new GrahamScan();
        frame.getContentPane().add(app);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(10, 10, 500, 500);
        frame.setTitle("GrahamScan");
        frame.setVisible(true);
    }

    static double area2(Point a,Point b,Point c){//符号付き面積を返す
	    return (b.getX()-a.getX())*(c.getY()-a.getY())-(b.getY()-a.getY())*(c.getX()-a.getX());
    }

    public void paintComponent(Graphics g){
        ArrayList<Point> al = new ArrayList<>();
        ArrayList<Point> al2;
        ArrayList<Point> al3 = new ArrayList<>();

        al.add(new Point(6,12));
        al.add(new Point(14,8));
        al.add(new Point(1,11));
        al.add(new Point(2,4));
        al.add(new Point(18,22));
        al.add(new Point(8,5));
        al.add(new Point(11,10));
        al.add(new Point(3,19));
        al.add(new Point(9,23));
        al.add(new Point(16,3));
        al.add(new Point(21,13));
        al.add(new Point(10,18));
        
        System.out.println(al);

	    Collections.sort(al,new Comparator<Point>() {//x座標でソート
		    @Override
		    public int compare(Point a,Point b){
		        return a.getX()-b.getX();
		    }
        });
        System.out.println(al);
        al2=new ArrayList<Point>(al);//al1の値をal2にコピー
        //System.out.println(al2);
        al2.remove(0);

        Collections.sort(al2,new Comparator<Point>() {//偏角の順にソート
            @Override
            public int compare(Point a,Point b){
                return -1*(int)(area2(al.get(0),a,b));
            }
        });
        //System.out.println(al);
        al2.add(0,al.get(0));
        System.out.println(al2);
        //System.out.println(al2.size());
        
        al3.add(al2.get(0));
        al3.add(al2.get(1));
        int cnt=0;

        for(int i=3;i<al2.size()+1;i++){
            System.out.println(al3);
            System.out.println(i);
            System.out.println(al2.get(i-1));
            if(area2(al3.get(cnt),al3.get(cnt+1),al2.get(i-1)) > 0){
                System.out.println("cnt:"+cnt);
                cnt++;
                al3.add(al2.get(i-1));
            }else{
                while(area2(al3.get(cnt),al3.get(cnt+1),al2.get(i-1)) < 0){
                    al3.remove(al3.size()-1);
                    cnt--;
                    System.out.println("cnt:"+cnt);
                }
                al3.add(al2.get(i-1));
                cnt++;
            }
        }
        System.out.println(al3);


        //描画
        g.setColor(Color.BLUE);
	    int s=15;//scale
        for(int i=1;i<al3.size();i++){//点を表示
            if(i==al3.size()-1){
                g.drawLine(al3.get(i).getX()*s,al3.get(i).getY()*s,al3.get(0).getX()*s,al3.get(0).getY()*s);
            }
            g.drawLine(al3.get(i-1).getX()*s,al3.get(i-1).getY()*s,al3.get(i).getX()*s,al3.get(i).getY()*s);
            
            //g.fillOval(al.get(i).getX()*s,al.get(i).getY()*s,10,10);
        }
        int d=10;
        for(int i=0;i<al2.size();i++){
            g.fillOval(al.get(i).getX()*s-d/2,al.get(i).getY()*s-d/2,d,d);
        }
    }

}

class Point{//点の座標を表す
    private int x;
    private int y;

    public  Point(int x,int y){//コンストラクタ
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String toString(){
        return "("+x+","+y+")";
    }
}
