package com.example.drawlines;
import java.security.PublicKey;

import android.graphics.PorterDuff;  
import android.graphics.PorterDuffXfermode; 

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.Display;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector; 
import java.util.Random;


 

//�½�һ����̳�View

public class Drawl extends View{

 private int mov_x;//�����������
 private int mov_y;
 private int width;
 private int height;
 private Paint paint;//��������
 //private Paint paint2;
 private Canvas canvas;//����
 private Bitmap bitmap;//λͼ
 private static int lineNum;
 private float edge;
 private float mir;
 private static int rectNum;
 static int player=0;//0 or 1
 private int PLAY_MODE=0;//0Ϊ�˻���ս��1Ϊ˫�˶�ս
 static float[] pts_delete=new float[1000];
 float[] gridLines = new float[1000];
 float[] redLines = new float[1000];
 int[] Player0Rect = new int[1000];
 static int Player0RectNum;
 int[] Player1Rect = new int[1000];
 static int Player1RectNum;
 int[][][] rectLines = new int[1000][4][4];
 public static int[][] rectRedIndex=new int[1000][5];
 
 
 //private int blcolor;
 private int first=0;
 public static int InputNum = 5;
 public Drawl(Context context) {
  super(context);
  //if(first==0)
  if(1==1)
  {
  paint=new Paint(Paint.DITHER_FLAG);//����һ������

  //************************initial the Player information*******************
  
  
//************************computer the grid lines***********************
  DisplayMetrics dm = new DisplayMetrics();
  WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
  Display display = wm.getDefaultDisplay();
  display.getMetrics(dm);
  width = dm.widthPixels;//���
  height = dm.heightPixels ;//�߶�
  rectNum = InputNum*InputNum;
  formGrid(InputNum);
  formRect(InputNum);
  
  
  bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); //����λͼ�Ŀ��
  canvas=new Canvas();
  canvas.setBitmap(bitmap);
  
  paint.setStyle(Style.STROKE);//���÷����
  paint.setStrokeWidth(10);//�ʿ�5����
  paint.setColor(Color.RED);//����Ϊ���
  paint.setAntiAlias(true);//��ݲ���ʾ
  }
 }

//��λͼ
 @Override
 protected void onDraw(Canvas canvas) {
  super.onDraw(canvas);
  
  canvas.drawBitmap(bitmap,0,0,null);
  
  paint.setColor(Color.BLACK);//���û�����ɫ  
  
  if(first==0)
  {
  first=1;
  }
  //paint.setStrokeWidth((float) 5.0);              //�߿�
      /*canvas.drawLines(gridLines, paint);//���ƶ���ֱ��  
	  //canvas.drawLines(pts, paint);//���ƶ���ֱ��  
	  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//��Ƥ��һ������   
      canvas.drawLines(pts_delete, paint);
      paint.setXfermode(null);//ȡ������ģʽ  
      paint.setColor(Color.RED);
	  canvas.drawLines(pts_delete, paint);
	  */
	  if(first==1)
	  {
		  int _ifChangePlayer=0;
		  for(int i=0;i<rectNum;i++)
		  {
			  if(rectIfFilled(i)==1)
			  {
				  canvas.save();
				  //Rect rect = new Rect (40,60,540,760) ;  
				  Rect rect = new Rect (rectLines[i][0][0],rectLines[i][1][1],rectLines[i][2][0],rectLines[i][3][1]) ;  
			      canvas.clipRect(rect)  ;
			      if(findInPlay0Rect(i)==1)
				  {
					  canvas.drawColor(Color.YELLOW);
				  }
			      else if(findInPlay1Rect(i)==1)
			      {
			    	  canvas.drawColor(Color.GREEN);
			      }
			      else
			      {
			    	  if (player==1)
				      {
				    	  canvas.drawColor(Color.YELLOW);
				    	  PushPlay0Rect(i);
				      }
				      else
				      {
				    	  canvas.drawColor(Color.GREEN);
				    	  PushPlay1Rect(i);
				      }  
			    	  _ifChangePlayer=1;
			      }
			      
			      canvas.restore();
			  }
		  }
		  if(_ifChangePlayer==1)
		  {
			  changePlayer();
		  }
		  //canvas.save();
		  //Rect rect = new Rect (40,60,540,760) ;
		  /*
		  Rect rect = new Rect (rectLines[0][0][0],rectLines[0][1][1],rectLines[0][2][0],rectLines[0][3][1]) ;  
	      canvas.clipRect(rect)  ;  
	      canvas.drawColor(Color.YELLOW);
	      canvas.restore();*/
	  }
	  
	  canvas.drawLines(gridLines, paint);//���ƶ���ֱ��  
	  //canvas.drawLines(pts, paint);//���ƶ���ֱ��  
	  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//��Ƥ��һ������   
      canvas.drawLines(pts_delete, paint);
      paint.setXfermode(null);//ȡ������ģʽ  
      paint.setColor(Color.RED);
	  canvas.drawLines(pts_delete, paint);
	  //*************���һ����ʾΪ��ɫ
	  canvas.save();
	  paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));//��Ƥ��һ������
	  float []lastLine= new float[4];
	  for(int i=0; i<lineNum*4;i=i+4)
	  {
		  if(pts_delete[i+4]==0)
		  {
			  lastLine[0]=pts_delete[i];
			  lastLine[1]=pts_delete[i+1];
			  lastLine[2]=pts_delete[i+2];
			  lastLine[3]=pts_delete[i+3];
			  break;
		  }
	  }
      canvas.drawLines(lastLine, paint);
      paint.setXfermode(null);//ȡ������ģʽ  
      paint.setColor(Color.MAGENTA);
      canvas.drawLines(lastLine, paint);
      canvas.restore();
      
	  canvas.save();
	  paint.setColor(Color.BLACK);
	  paint.setTextSize(50);
	  paint.setStrokeWidth(3);
	  canvas.drawText("�Ʒ��÷֣�"+Player0RectNum, edge+50, width+90, paint); 
	  canvas.drawText("�̷��÷֣�"+Player1RectNum, width/2+50, width+90, paint);
	  if (player==0)
	  {
		  paint.setColor(Color.RED);
		  paint.setStrokeWidth(10);
		  canvas.drawRect(edge, width+20, width/2, width+120, paint);
		  paint.setColor(Color.BLACK);
		  paint.setStrokeWidth(3);
		  canvas.drawRect(width/2, width+20, width-edge, width+120, paint);
	  }
	  else
	  {
		  paint.setColor(Color.RED);
		  paint.setStrokeWidth(10);
		  canvas.drawRect(width/2, width+20, width-edge, width+120, paint);
		  paint.setColor(Color.BLACK);
		  paint.setStrokeWidth(3);
		  canvas.drawRect(edge, width+20, width/2, width+120, paint);
		  
	  }
	  
	  paint.setTextSize(80);
	  canvas.drawText("���¿�ʼ", width/2+90, width+380, paint);
	  canvas.drawRect(width/2+50, width+250, width/2+450, width+450, paint);
	  if(PLAY_MODE==0)
	  {
		  canvas.drawText("˫��ģʽ", edge+90, width+380, paint);
	  }
	  else
	  {
		  canvas.drawText("�˻�ģʽ", edge+90, width+380, paint);
	  }
	  canvas.drawRect(edge+50, width+250, width/2-50, width+450, paint);
	  canvas.restore();
	  paint.setStrokeWidth(10);
	  
	  if (PLAY_MODE==0 && player==1)
	  {
		  Timer timer=new Timer();//ʵ����Timer��
		  //timer.schedule(null, 1000);
		  //setTimeout();
		  //robertPlay();
		  /*
		   timer.schedule(new TimerTask(){
		   public void run(){
		   robertPlay();}
		   },1000);//��ٺ���*/
		   
		   for(long i=0;i<20000000;i++)
		   {}
		   robertPlay();
		   invalidate();
	  }
	  
 }
 
 //�����¼�
 @Override
 public boolean onTouchEvent(MotionEvent event) {
  /*if (event.getAction()==MotionEvent.ACTION_MOVE) {//����϶�
   paint.setColor(Color.RED);
   canvas.drawLine(mov_x, mov_y, event.getX(), event.getY(), paint);//����
   invalidate();
  }*/
  if (event.getAction()==MotionEvent.ACTION_DOWN) {//������
   mov_x=(int) event.getX();
   mov_y=(int) event.getY();
   paint.setColor(Color.RED);
   
   if(mov_x>width/2+50 && mov_x<width/2+450 && mov_y>width+250 &&mov_y<width+450)
   {
	   RestartGame();
	   invalidate();
	   return true;
   }
   if(mov_x>edge+50 && mov_x<width/2-50 && mov_y>width+250 &&mov_y<width+450)
   {
	   if(PLAY_MODE==0)
	   {
		  PLAY_MODE=1;
	   }
	   else
	   {
		  PLAY_MODE=0;
	   }
	   invalidate();
   }
	  
   int index=getTouchLineStartIndex(mov_x,mov_y);
   if(index!=10000)
   {
	   if(setPtsRed(mov_x,mov_y)==1)
	   {
		   changePlayer();
		   invalidate();
	   }
   }
   
   //invalidate();

  }

  mov_x=(int) event.getX();
  mov_y=(int) event.getY();
  return true;
 }
 
 public void formGrid(int x)
 {
	 float w= (float) (width *0.9);
	 float h= (float) (height * 0.9);
	 float l=(w>h)?h:w;
	 edge = (float) (l/0.9 *0.05);
	 mir = l/x;
	 float end = edge+l;
	 lineNum = 2*(x+1)*x;
	 float [][]line = new float[lineNum][4];
	 int lineIndex=0;
	 for (int i=0; i<x+1;i++)
	 {
		 for(int j=0;j<x;j++)
		 {
			 line[lineIndex][0] = edge+i*mir  ;
			 line[lineIndex][1] = edge+j*mir  ;
			 line[lineIndex][2] = edge+i*mir  ;
			 line[lineIndex][3] = edge+(j+1)*mir  ;
			 lineIndex++;
		 }
	 }
	 for (int i=0; i<x+1;i++)
	 {
		 for(int j=0;j<x;j++)
		 {
			 line[lineIndex][0] = edge+j*mir  ;
			 line[lineIndex][1] = edge+i*mir  ;
			 line[lineIndex][2] = edge+(j+1)*mir  ;
			 line[lineIndex][3] = edge+i*mir  ;
			 lineIndex++;
		 }
	 }
	 
	 for (int i=0; i<lineNum; i++)
	 {
		 gridLines[i*4]=line[i][0];
		 gridLines[i*4+1]=line[i][1];
		 gridLines[i*4+2]=line[i][2];
		 gridLines[i*4+3]=line[i][3];
	 }
 }
 
 public void formRect(int x)
 {
	 int index=0;
	 for(int i=0; i<x;i++)//���м���,iΪ�к�
	 {
		 for(int j=0; j<x;j++)//����
		 {
			//left
			 rectLines[index][0][0]=(int)(edge+(mir*j));
			 rectLines[index][0][1]=(int)(edge+(mir*i));
			 rectLines[index][0][2]=(int)(edge+(mir*j));
			 rectLines[index][0][3]=(int)(edge+(mir*(i+1)));
			 
			 //top
			 rectLines[index][1][0]=(int)(edge+(mir*j));
			 rectLines[index][1][1]=(int)(edge+(mir*i));
			 rectLines[index][1][2]=(int)(edge+(mir*(j+1)));
			 rectLines[index][1][3]=(int)(edge+(mir*i));
			 
			 //right
			 rectLines[index][2][0]=(int)(edge+(mir*(j+1)));
			 rectLines[index][2][1]=(int)(edge+(mir*i));
			 rectLines[index][2][2]=(int)(edge+(mir*(j+1)));
			 rectLines[index][2][3]=(int)(edge+(mir*(i+1)));
			 
			 //bottom
			 rectLines[index][3][0]=(int)(edge+(mir*j));
			 rectLines[index][3][1]=(int)(edge+(mir*(i+1)));
			 rectLines[index][3][2]=(int)(edge+(mir*(j+1)));
			 rectLines[index][3][3]=(int)(edge+(mir*(i+1)));
			 
			 index++;
		 }
	 }
	 
 }
 
 public int getTouchLineStartIndex(int x, int y) //����touch����gridlines��index��
 {
	 int i;
	 for( i=0; i<lineNum*4; i=i+4)
	 {
		 if(gridLines[i]==gridLines[i+2] && x>gridLines[i]-50 &&x<gridLines[i]+50 && y>gridLines[i+1] && y<gridLines[i+3])
		 {
			 break;
		 }
		 else if(gridLines[i+1]==gridLines[i+3] && y>gridLines[i+1]-50 && y<gridLines[i+1]+50 && x>gridLines[i] && x<gridLines[i+2])
		 {
			 break;
		 }
	 }
	 if(i==lineNum*4)
		 return 10000;
	 else
		 return i;
	
 }
 
 public int setPtsRed(int x , int y)
 {
	 int _ifnew=0;
	 int index=getTouchLineStartIndex(x,y);
	 if(index==10000)
		 return _ifnew;
	 else
	 {
		 for(int i=0; i<lineNum*4; i=i+4)
		 {
			 if (pts_delete[i]==0)
			 {
				 for(int cycle=0; cycle<4;cycle++)
				 {
					pts_delete[i+cycle]=gridLines[index+cycle];	 
				 }
				 _ifnew=1;
				 break;
			 }
			 else if(pts_delete[i]==gridLines[index] && pts_delete[i+1]==gridLines[index+1] && pts_delete[i+2]==gridLines[index+2] && pts_delete[i+3]==gridLines[index+3])
			 {
				 break;
			 }
		 }
		 return _ifnew;
	 }
 }
 
 public int rectIfFilled(int index)
 {
	 int[] filled = new int[4];
	 for(int i=0; i<4; i++)
	 {
		 int j=0;
		 for(j=0;j<lineNum*4;j=j+4)
		 {
			 if((int)(pts_delete[j])==rectLines[index][i][0] 
				&&(int)(pts_delete[j+1])==rectLines[index][i][1]	
				&&(int)(pts_delete[j+2])==rectLines[index][i][2]	
				&&(int)(pts_delete[j+3])==rectLines[index][i][3])
			 {
				break;
			 }
		 }
		 filled[i] = j;
	 }
	 if(filled[0]!=lineNum*4 &&filled[1]!=lineNum*4 &&filled[2]!=lineNum*4 &&filled[3]!=lineNum*4)
		 return 1;
	 else
		 return 0; 
 }
 public void changePlayer()
 {
	 if(player==0)
	 {
		 player=1;
	 }
	 else
	 {
		 player=0;
	 }
 }
 public int findInPlay0Rect(int index)
 {
	 int _ifFind=0;
	 for (int i=0;i<Player0RectNum;i++)
	 {
		 if (Player0Rect[i]==index)
		 {
			 _ifFind=1;
			 break;
		 }
	 }
	 return _ifFind;
 }
 public int findInPlay1Rect(int index)
 {
	 int _ifFind=0;
	 for (int i=0;i<Player1RectNum;i++)
	 {
		 if (Player1Rect[i]==index)
		 {
			 _ifFind=1;
			 break;
		 }
	 }
	 return _ifFind;
 }
 public void PushPlay0Rect(int index)
 {
	 if (findInPlay0Rect(index)==0)
	 {
		 Player0RectNum++;
		 Player0Rect[Player0RectNum-1]=index;
	 }
 }
 public void PushPlay1Rect(int index)
 {
	 if (findInPlay1Rect(index)==0)
	 {
		 Player1RectNum++;
		 Player1Rect[Player1RectNum-1]=index;
	 }
 }
 public static void RestartGame()
 {
	 Player0RectNum=0;
	 Player1RectNum=0;
	 player=0;
	 for (int i=0;i<lineNum*4;i=i+4)
	 {
		 pts_delete[i]=0;
		 pts_delete[i+1]=0;
		 pts_delete[i+2]=0;
		 pts_delete[i+3]=0;
	 }
	 for(int index=0;index<rectNum;index++)
	 {
		 for(int i=0; i<5; i++)
		 {
			 rectRedIndex[index][i]=0;
		 } 
	 }
	 
 }
 public void robertPlay()
 {
	 //��һ�ж�RECT�м������ߣ�ѡ��3���Ļ�����һ�������û��3�еģ���ѡ��RECT�������ٵ�һ����
	 setRectRedNum();
	 int full=0;
	 Vector rec0Red = new Vector();
	 Vector rec1Red = new Vector();
	 Vector rec2Red = new Vector();
	 Vector rec3Red = new Vector();
	 Vector rec4Red = new Vector();
	 /*
	 int []rect0Red = new int[225]; 
	 int []rect1Red = new int[225]; 
	 int []rect2Red = new int[225]; 
	 int []rect3Red = new int[225]; 
	 int []rect4Red = new int[225];*/
	 for (int i=0; i<rectNum;i++ )
	 {
		 switch(rectRedIndex[i][4])
		 {
		 	case 0: rec0Red.add(i);break;
		 	case 1: rec1Red.add(i);break;
		 	case 2: rec2Red.add(i);break;
		 	case 3: rec3Red.add(i);break;
		 	case 4: rec4Red.add(i);break;
		 } 
	 }
	 int selectRectIndex=0;
	 int selectLineIndex=0;
	 if(rec3Red.size()!=0)
	 {
		 int a = (int)(Math.random() * 100);
		 int b = a%rec3Red.size();
		 selectRectIndex = (Integer) rec3Red.elementAt(b);
		 for (int i=0; i<4; i++)
		 {
			 if (rectRedIndex[selectRectIndex][i]==0)
			 {
				 selectLineIndex=i;
				 break;
			 }
		 }
	 }
	 else if(rec0Red.size()!=0)
	 {
		 int a = (int)(Math.random() * 100);
		 int b = a%rec0Red.size();
		 int []random = new int[4];
		 int ranIndex=0;
		 selectRectIndex = (Integer) rec0Red.elementAt(b);
		 for (int i=0; i<4; i++)
		 {
			 if (rectRedIndex[selectRectIndex][i]==0)
			 {
				 random[ranIndex]=i;
				 ranIndex++;
				 //selectLineIndex=i;
				 //break;
			 }
		 }
		 int c = a%4;
		 selectLineIndex=random[c];
	 }
	 else if(rec1Red.size()!=0)
	 {
		 int a = (int)(Math.random() * 100);
		 int b = a%rec1Red.size();
		 int []random = new int[4];
		 int ranIndex=0;
		 selectRectIndex = (Integer) rec1Red.elementAt(b);
		 for (int i=0; i<4; i++)
		 {
			 if (rectRedIndex[selectRectIndex][i]==0)
			 {
				 random[ranIndex]=i;
				 ranIndex++;
				 //selectLineIndex=i;
				 //break;
			 }
		 }
		 int c = a%3;
		 selectLineIndex=random[c];
	 }
	 else if(rec2Red.size()!=0)
	 {
		 int a = (int)(Math.random() * 100);
		 int b = a%rec2Red.size();
		 int []random = new int[4];
		 int ranIndex=0;
		 selectRectIndex = (Integer) rec2Red.elementAt(b);
		 for (int i=0; i<4; i++)
		 {
			 if (rectRedIndex[selectRectIndex][i]==0)
			 {
				 random[ranIndex]=i;
				 ranIndex++;
				 //selectLineIndex=i;
				 //break;
			 }
		 }
		 int c = a%2;
		 selectLineIndex=random[c];
	 }
	 else 
	 {
		 full=1;
	 }
	 if(full==0)
	 {
		 int x = (rectLines[selectRectIndex][selectLineIndex][0]+rectLines[selectRectIndex][selectLineIndex][2])/2;
		 int y = (rectLines[selectRectIndex][selectLineIndex][1]+rectLines[selectRectIndex][selectLineIndex][3])/2;
		 if(setPtsRed(x,y)==1)
		   {
			   changePlayer();
			   /*
			   try {
			        Thread.sleep(1000);//���������5000����5000���룬Ҳ����5�룬���Ըó�����Ҫ��ʱ��
			} catch (InterruptedException e) {
			        e.printStackTrace();
			}*/
			   //Thread.currentThread().sleep(1000);
			   //invalidate();
		   }
	 } 
	 rec0Red.clear();
	 rec1Red.clear();
	 rec2Red.clear();
	 rec3Red.clear();
 }
 
 public void setRectRedNum()
 {
	 for(int index=0;index<rectNum;index++)
	 {
		 for(int i=0; i<4; i++)
		 {
			 int j=0;
			 for(j=0;j<lineNum*4;j=j+4)
			 { 
				 if((int)(pts_delete[j])==rectLines[index][i][0] 
					&&(int)(pts_delete[j+1])==rectLines[index][i][1]	
					&&(int)(pts_delete[j+2])==rectLines[index][i][2]	
					&&(int)(pts_delete[j+3])==rectLines[index][i][3])
				 {
					rectRedIndex[index][i]=1;
					break;
				 }
			 }
		 } 
		 rectRedIndex[index][4]=rectRedIndex[index][0] + rectRedIndex[index][1] +rectRedIndex[index][2] +rectRedIndex[index][3];
	 }
	 
	 
 }
}