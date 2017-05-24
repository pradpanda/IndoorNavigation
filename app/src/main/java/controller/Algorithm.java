package controller;


import android.util.Log;

public class Algorithm {

    String[][] adjlist;

    Point[] v=null;
    String[] q;
    String[][] color;
    int c;
    int length;

    public Point[] algo(String source,String destination){
        try {

            int c1;
            int i=0;
            int j=0;
            String s;


            c=20;

            //v=new Point[27];

            ;


            List l = new List();

           // Log.e("hello",""+l.point.length);
            v=l.entry();
            length = v.length;
            color= new String[length][2];



            adjlist=l.addAdjList();
            Log.e("Algorithm ","hello "+length);
            Log.e("Algorithm ","hello how r u  "+String.valueOf(adjlist.length));
            for(i=0;i<length;i++){
                color[i][0]=String.valueOf(v[i].p);
                color[i][1]="Blue";
            }

//


        }
//
        catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }


        BFS(source, destination);
        System.out.println(c);
        c=v.length;
        Point point =new Point();
        int pos=getPos(destination);
        System.out.println("position of destination is  "+pos);
        point.p=v[pos].p;
        point.parent=v[pos].parent;
        int count=0;
        while(!point.parent.equals("0")){
            //System.out.println("parent "+ point.parent);
            pos=getPos(point.parent);
            point=v[pos];


            count++;
        }
        count++;
        Point[] path= new Point[count];
        pos=getPos(destination);

        for(int i=0;i<count;i++){
            path[i]=v[pos];
            pos=getPos(path[i].parent);


        }
    Log.e("Algorithm ", String.valueOf(path.length));
        //System.out.println("counter  " +count);


        return path;
    }



//	public  void createAdjlist(char[] p, char[] adj, int j){
//		for(int i =0;i<j;i++){
//			adj[i]=p[i];
//		}
//	}

    public void BFS(String source, String destination){
        String u,adjV;
        int pos,cpos,listpos,l,flag=0;
        q=new String[3];
        pos = enque(source,0);
        cpos=getPos(source);
        v[cpos].parent="0";
        color[cpos][1]="Green";
        while(q[0]!="0"){

            u=dequeue(pos);
            pos--;
            //System.out.println(u);
            cpos=getPos(u);
            if(color[cpos][1].equals("Green")){
                color[cpos][1]="Red";
                listpos = getListPos(v[cpos].p);
                Log.e("Algo",listpos+"  "+v[cpos].p);
                Log.e("algorithm",String.valueOf(listpos));
                l=adjlist[listpos].length;

                for(int i=1;i<l;i++){
                    adjV=adjlist[listpos][i];
                    System.out.println("checking adj  "+adjV);
                    cpos=getPos(adjV);
                    if(cpos!=-1){
                        System.out.println(cpos+"  "+adjV);
                        if(color[cpos][1].equals("Blue")){
                            color[cpos][1]="Green";
                            if(destination!=adjV){
                                pos=enque(adjV,pos+1);
                                v[cpos].parent=u;
                                System.out.println("hello  "+u);
                                flag=0;
                            }
                            //System.out.println(pos);
                            else{
                                v[cpos].parent=u;
                                flag=1;
                                break;
                            }
                        }

                    }
                    if(flag==1){
                        break;
                    }
                }
                //for(int k=0;k<q.length;k++)
                //System.out.println(q[k]);
                //System.out.println();

            }
        }




    }
    public  int getListPos(String u){
        int pos=0;
        for(int i=0;i<length;i++){
            if(adjlist[i][0]==u)
                return i;
        }
        return -1;
    }

    public int getPos(String u){
        int i=0;
        for(i=0;i<length;i++){
            if((v[i].p).equals(u))
                return i;
        }
        return -1;
    }

    public  int enque(String a,int pos){

        pos= put(pos,a);
        return pos;

    }
    public  String dequeue(int pos){
        String c;
        c= q[0];
        for(int i=1;i<=pos;i++){
            q[i-1]=q[i];
        }
        q[pos]="0";
        return c;
    }
    public  int put(int position, String value)
    {
        if (position >= q.length)
        {
            int newSize = 2 * q.length;
            if (position >= newSize)
                newSize = 2 * position;
            String[] newData = new String[newSize];
            System.arraycopy(q, 0, newData, 0,q.length);
            q = newData;
            // System.out.println("Size of dynamic array increased to " + newSize);
        }
        q[position] = value;

        return position;
    }

}

