import com.jogamp.opengl.*;
import com.jogamp.opengl.util.PMVMatrix; 
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.KeyEvent;



public class SimpleExample6Objs implements GLEventListener{
  Object3D[] objs;
  PMVMatrix mats;
  float t=0;
  Vec3 lightpos;
  Vec3 lightcolor;
  static final int SCREENH=320;
  static final int SCREENW=320;


  public SimpleExample6Objs(){
    objs = new Object3D[6];
    objs[0] = new Cylinder2(18,0.03f,0.5f,true, new Vec4(0.7f,0.7f,0.7f,1.0f),
                            new ImageLoader("resource/image/noise.png"));
    objs[1] = new Cylinder2(4,0.3f,0.2f,false,  new Vec4(0.21f,0.51f,0.28f,1.0f),
                            new ImageLoader("resource/image/BrickNormalMap.png"));
    objs[2] = new Cylinder2(18,0.3f,0.06f,true, new Vec4(0.7f,0.7f,0.2f,1.0f),
                            new ImageLoader("resource/image/flatwhite.png"));
    objs[3] = new Cylinder2(4,0.3f,0.8f,false, new Vec4(0.2f,0.2f,0.2f,1.0f),
                            new ImageLoader("resource/image/MetalNormal.png"));
    objs[4] = new Cylinder2(18,0.3f,0.8f,true, new Vec4(0.7f,0.7f,0.7f,1.0f),
                            new ImageLoader("resource/image/FabricNormal.png"));
    objs[5] = new Cylinder2(4,0.1f,0.8f,false, new Vec4(0.4f,0.6f,0.1f,1.0f),
                            new ImageLoader("resource/image/BambooNormal.png"));
    //obj = new BezierPatch();
    mats = new PMVMatrix(PMVMatrix.INVERSE_TRANSPOSED_MODELVIEW);
    /*
    addKeyListener(new simpleExampleKeyListener());
    addMouseMotionListener(new simpleExampleMouseMotionListener());
    addMouseListener(new simpleExampleMouseListener());
    */
  }

  public void init(GLAutoDrawable drawable){
    drawable.setGL(new DebugGL2(drawable.getGL().getGL2()));
    final GL2 gl = drawable.getGL().getGL2();
    gl.glViewport(0, 0, SCREENW, SCREENH);

    // Clear color buffer with black
    gl.glClearColor(0.7f, 0.7f, 0.5f, 1.0f);
    gl.glClearDepth(1.0f);
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT,1);
    gl.glFrontFace(GL.GL_CCW);
    gl.glEnable(GL.GL_CULL_FACE);
    gl.glCullFace(GL.GL_BACK);

    Shader shader0 =new Shader("resource/shader/spot.vert",
                               "resource/shader/spot.frag");
    Shader shader1 =new Shader("resource/shader/bumpmapping.vert",
                               "resource/shader/bumpmapping.frag");
    Shader shader2 =new Shader("resource/shader/simple.vert",
                               "resource/shader/simple.frag");
    Shader shader3 =new Shader("resource/shader/simpleshade.vert",
                               "resource/shader/simpleshade.frag");
    Shader shader4 =new Shader("resource/shader/simpletexture.vert",
                               "resource/shader/simpletexture.frag");
    Shader shader5 =new Shader("resource/shader/chalk.vert",
                               "resource/shader/chalk.frag");
    Shader shader6 =new Shader("resource/shader/brick.vert",
                               "resource/shader/brick.frag");
    Shader shader7 =new Shader("resource/shader/plastic.vert",
                               "resource/shader/plastic.frag");
    Shader shader8 =new Shader("resource/shader/fabric.vert",
                               "resource/shader/fabric.frag");
    Shader shader9 =new Shader("resource/shader/last.vert",
                               "resource/shader/last.frag");                
    shader0.init(gl);
    shader1.init(gl);
    shader2.init(gl);
    shader3.init(gl);
    shader4.init(gl);
    shader5.init(gl);
    shader6.init(gl);
    shader7.init(gl);
    shader8.init(gl);
    shader9.init(gl);


    objs[0].init(gl, mats, shader5);
    objs[1].init(gl, mats, shader6);
    objs[2].init(gl, mats, shader1);//modified shader1
    objs[3].init(gl, mats, shader7);
    objs[4].init(gl, mats, shader8);
    objs[5].init(gl, mats, shader9);
    
    //objs.init(gl, mats, null);
    gl.glUseProgram(0);
    lightpos = new Vec3(0.0f, 0.0f, 30f);
    lightcolor = new Vec3(1f, 1f, 1f);
  }

  public void display(GLAutoDrawable drawable){
    final GL2 gl = drawable.getGL().getGL2();
    if(t<360){
      t = t+0.3f;
    }else{
      t = 0f;
    }
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    mats.glMatrixMode(GL2.GL_PROJECTION);
    mats.glLoadIdentity();
    mats.glFrustumf(-0.5f, 0.5f, -0.5f, 0.5f, 1f, 100f);
    mats.glMatrixMode(GL2.GL_MODELVIEW);
    mats.glLoadIdentity();
    mats.glTranslatef(0f,0f,-4.0f);
    for(int i=0; i<6; i++){
      mats.glPushMatrix();
      mats.glTranslatef((i%3)-1f,0.7f-(i/3)*1.4f,0f);
      //mats.glTranslatef(0f,-0.4f+0.8f*(float)Math.sin((t+90)/180.0*Math.PI),0f);
      mats.glRotatef(t,0.3f,1f,0f);
      mats.glRotatef(90,1f,0f,0f);
      mats.glRotatef(45,0f,0f,1f);
      mats.update();
      objs[i].display(gl, mats, lightpos, lightcolor);
      mats.glPopMatrix();
    }
    gl.glFlush();
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h){
  }

  public void dispose(GLAutoDrawable drawable){
  }

  public KeyListener createKeyListener(){
    return new simpleExampleKeyListener();
  }

  public MouseListener createMouseListener(){
    return new simpleExampleMouseListener();
  }

  public static void main(String[] args){
    SimpleExample6Objs t = new SimpleExample6Objs();
    new SimpleExampleBase("SimpleExample6Objs", t, SCREENW, SCREENH).
      addKeyListener(t.createKeyListener()).
      addMouseListener(t.createMouseListener()).
      start();
  }
  
  class simpleExampleKeyListener implements KeyListener{
    public void keyPressed(KeyEvent e){
      int keycode = e.getKeyCode();
      System.out.print(keycode);
      switch(keycode){
      case com.jogamp.newt.event.KeyEvent.VK_LEFT:
        System.out.println("left key");
        break;
      case com.jogamp.newt.event.KeyEvent.VK_RIGHT:
        System.out.println("right key");
        break;
      case com.jogamp.newt.event.KeyEvent.VK_UP:
        System.out.println("up key");
        break;
      case com.jogamp.newt.event.KeyEvent.VK_DOWN:
        System.out.println("down key");
        break;
      }
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyTyped(KeyEvent e){
    }
  }
  
  class simpleExampleMouseListener implements MouseListener{
    public void mouseDragged(MouseEvent e){
      System.out.println("dragged:"+e.getX()+" "+e.getY());
    }
    public void mouseMoved(MouseEvent e){
      System.out.println("moved:"+e.getX()+" "+e.getY());
    }
    public void mouseWheelMoved(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
      System.out.println("clicked:"+e.getX()+" "+e.getY());
    }
    public void mouseEntered(MouseEvent e){
      System.out.println("entered:");
    }
    public void mouseExited(MouseEvent e){
      System.out.println("exited:");
    }
    public void mousePressed(MouseEvent e){
      System.out.println("pressed:"+e.getX()+" "+e.getY());
    }
    public void mouseReleased(MouseEvent e){
      System.out.println("released:"+e.getX()+" "+e.getY());
    }
  }

}
