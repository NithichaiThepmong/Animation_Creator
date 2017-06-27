//////////////////////
//Nithichai Thepmong//
//    Assigment2    //
//Animation Creator //
//////////////////////
class Amaker{
  /*0 = blank 
    1 = tile can move
    2 = tile can't move
    3 = tile ready to move;
    4 = path to tile move on*/ 
  private int aWidth, aHeight;
  private int[][][] frame;
  private int maxFrame;
  private int currFrame;
  private int indexFrame;
  
  public Amaker(int aWidth, int aHeight){
    this.aWidth = aWidth;
    this.aHeight = aHeight;
    maxFrame = 1000;
    currFrame = 0;
    indexFrame = 0;
    ///// Set All to Zero /////
    frame = new int[maxFrame][aHeight][aWidth];
    for(int j = 0; j < aHeight; j++){
      for(int k = 0; k < aWidth; k++){
        frame[currFrame][j][k] = 0;
      }
    }
  }
  
  public void addTile(int x, int y){
    frame[currFrame][y-1][x-1] = 1;
  }
  
  public void addTileAll(){
    for(int i = 0; i < aHeight; i++){
      for(int j = 0; j < aWidth; j++){
        addTile(j+1, i+1);
      }
    }
  }
  
  public void removeTile(int x, int y){
    frame[currFrame][y-1][x-1] = 0;
  }
  
  public void moveTile(int x1, int y1, int x2, int y2){
    frame[currFrame][y2-1][x2-1] = 2;
    frame[currFrame][y1-1][x1-1] = 0;
    unsetMoveTile();
  }
  
  ///// remove path to tile move on /////
  public void unsetMoveTile(){
    for(int i = 0; i < aHeight; i++){
      for(int j = 0; j < aWidth; j++){
        if(frame[currFrame][i][j] == 4){
          ///// have path -> remove it /////
          frame[currFrame][i][j] = 0;
        }
        if(frame[currFrame][i][j] == 3){
          ///// tile ready to move -> tile can move /////
          frame[currFrame][i][j] = 1;
        }
      }
    }
  }
  
  ///// add path to tile move on ///// 
  public void setMoveTile(int x, int y){
    unsetMoveTile();
    ///// blank -> path
    frame[currFrame][y-1][x-1] = 3;
    if(x-2 >= 0)
      if(frame[currFrame][y-1][x-2] == 0){
        frame[currFrame][y-1][x-2] = 4;
      }
    if(x <= aWidth-1)
      if(frame[currFrame][y-1][x] == 0){
        frame[currFrame][y-1][x] = 4;
      }
    if(y-2 >= 0)
      if(frame[currFrame][y-2][x-1] == 0){
        frame[currFrame][y-2][x-1] = 4;
      }
    if(y <= aHeight - 1)
      if(frame[currFrame][y][x-1] == 0){
        frame[currFrame][y][x-1] = 4;
      }
  }

  public void addFrame(){
    if(indexFrame < maxFrame-1){
      for(int i = 0; i < aHeight; i++){
        for(int j = 0; j < aWidth; j++){
          if(frame[currFrame][i][j] == 4){
            ///// Path -> Blank /////
            frame[currFrame][i][j] = 0;
          }else if(frame[currFrame][i][j] == 2){
            ///// Tile can't move -> Tile can move /////
            frame[currFrame][i][j] = 1;
          }else if(frame[currFrame][i][j] == 3){
            ///// Tile ready to move -> tile can move /////
            frame[currFrame][i][j] = 1;
          }
        }
      }
      
      int[][] frame2 = new int[aHeight][aWidth];
      for(int i = 0; i < aHeight; i++){
        for(int j = 0; j < aWidth; j++){
          if(frame[currFrame][i][j] == 1 || frame[currFrame][i][j] == 2){
            ///// Tile can't move -> Tile can move
            frame2[i][j] = 1;
          }else{
            ///// -> Blank /////
            frame2[i][j] = 0;
          }
        }
      }
      if(indexFrame == currFrame){
        currFrame++;
      }
      indexFrame++;
      frame[indexFrame] = frame2;
    }
  }

  public void removeFrame(){
    if(indexFrame > 0){
      resetFrame();
      if(indexFrame == currFrame){
        currFrame--;
      }
      indexFrame--;
    }
  }
  
  public void resetFrame(){
    for(int i = 0; i < aHeight; i++){
      for(int j = 0; j < aWidth; j++){
        frame[currFrame][i][j] = 0;
      }
    }
  }
  
  public String save(){
    println("frame " + (indexFrame+1));
    println("W " + aWidth);
    println("H " + aHeight);
    String save = "";
    ///// Format save oustide /////
    for(int k = 0; k <= indexFrame; k++){
      for(int i = 0; i < aHeight; i++){
        save += "\"";
        for(int j = 0; j < aWidth; j++){
          save += frame[k][i][j];
        }
        save += "\"";
        if(i != aHeight-1){
          save += "+\n";
        }
      }
      if(k != indexFrame){
        save += "+\n";
      }
    }
    println(save);
    ///// Use in programme /////
    save = "";
    for(int k = 0; k <= indexFrame; k++){
      for(int i = 0; i < aHeight; i++){
        for(int j = 0; j < aWidth; j++){
          save += frame[k][i][j];
        }
        if(i != aHeight-1){
        }
      }
    }
    return save;
  }
  
  public void load(String data, int frameSize, int w, int h){
    this.indexFrame = frameSize - 1;
    for(int k = 0; k < frameSize; k++){
      for(int i = 0; i < h; i++){
        for(int j = 0; j < w; j++){
          int index = (k * w * h) + (i * w) + j;
          frame[k][i][j] = int(str(data.charAt(index)));
        }
      }
    }
  }
  
  ///// Increase Width /////
  public void addWidth(){
    int[][][] frame2 = frame;
    frame = new int[maxFrame][aHeight][aWidth+1];
    for(int k = 0; k <= indexFrame; k++){
      for(int i = 0; i < aHeight; i++){
        for(int j = 0; j < aWidth; j++){
          frame[k][i][j] = frame2[k][i][j];
        }
      }
    }
    aWidth++;
  }
  
  ///// Decrease Width /////
  public void removeWidth(){
    int[][][] frame2 = frame;
    frame = new int[maxFrame][aHeight][aWidth-1];
    for(int k = 0; k <= indexFrame; k++){
      for(int i = 0; i < aHeight; i++){
        for(int j = 0; j < aWidth - 1; j++){
          frame[k][i][j] = frame2[k][i][j];
        }
      }
    }
    aWidth--;
  }
  
  ///// Increase Height /////
  public void addHeight(){
    int[][][] frame2 = frame;
    frame = new int[maxFrame][aHeight + 1][aWidth];
    for(int k = 0; k <= indexFrame; k++){
      for(int i = 0; i < aHeight; i++){
        for(int j = 0; j < aWidth; j++){
          frame[k][i][j] = frame2[k][i][j];
        }
      }
    }
    aHeight++;
  }
  
  ///// Decrease Height /////
  public void removeHeight(){
    int[][][] frame2 = frame;
    for(int k = 0; k <= indexFrame; k++){
      for(int i = 0; i < aHeight-1; i++){
        for(int j = 0; j < aWidth - 1; j++){
          frame[k][i][j] = frame2[k][i][j];
        }
      }
    }
    aHeight--;
  }
  
  public void displayCurrentFrame(){
    for(int i = 0; i < aHeight; i++){
      for(int j = 0; j < aWidth; j++){
        print(frame[currFrame][i][j]);
      }
      println();
    }
    println("=====");
  }
  
  public int getTotalNumFrame(){
    return frame.length;
  }
  
  public int getTotalNumTile(){
    int numTile = 0;
    for(int j = 0; j < aHeight; j++){
      for(int i = 0; i < aWidth; i++){
        if(frame[currFrame][j][i] == 1 || frame[currFrame][j][i] == 2 || frame[currFrame][j][i] == 3){
           numTile++;
        }
      }
    }
    return numTile;
  }
  
  public int[][][] getFrame(){
    return frame;
  }
  
  public int getCurrentFrame(){
    return currFrame;
  }
  
  public int getIndexFrame(){
    return indexFrame;
  }
  
  public int getMaxFrame(){
    return maxFrame;
  }
  
  public int getWidth(){
    return aWidth;
  }
  
  public int getHeight(){
    return aHeight;
  }
  
  public void setCurrentFrame(int currFrame){
    if(currFrame <= indexFrame && currFrame >= 0){
      this.currFrame = currFrame;
    }
  }
  
  public void setIndexFrame(int indexFrame){
    if(indexFrame < maxFrame && indexFrame >= 0){
      this.indexFrame = indexFrame;
    }
  }
  
}


class AViewer{
  private Amaker m;
  private int tileSize;
  private int x, y;
  private String theme;
  
  public AViewer(Amaker m){
    this.m = m;
    theme = "BW";
  }
  
  public void display(int x, int y, int count, boolean showTable){
    int[][][] frame = m.getFrame();
    drawBG();
    if(showTable){
      ///// If show -> showTable, showOldTile
      drawTable(x, y);
      displayOldTile(x, y, count);
    }
    noStroke();
    for(int i = 0; i < m.getHeight(); i++){
      for(int j = 0; j < m.getWidth(); j++){
        if(frame[count][i][j] == 1 || frame[count][i][j] == 2 || frame[count][i][j] == 3){
          ///// Color /////
          if(theme == "BW"){
            fill(0);
          }else if(theme == "RED"){
            fill(255, 0, 0);
          }else if(theme == "GREEN"){
            fill(0, 255, 0);
          }else if(theme == "BLUE"){
            fill(0, 0, 255);
          }else if(theme == "YELLOW"){
            fill(255, 255, 0);
          }
          ///// Style /////
          if(theme == "BW"){
            rect(x + j * tileSize, y + i * tileSize, tileSize, tileSize);
          }else{
            ellipse(x + j * tileSize + tileSize/2, y + i * tileSize + tileSize/2, tileSize  * 0.75, tileSize  * 0.75);
          }
        }else if(frame[count][i][j] == 4 && showTable  ){
          fill(100);
          ///// Style /////
          if(theme == "BW"){
            rect(x + j * tileSize, y + i * tileSize, tileSize, tileSize);
          }else{
            ellipse(x + j * tileSize + tileSize/2, y + i * tileSize + tileSize/2, tileSize  * 0.75, tileSize  * 0.75);
          }
        }
      }
    }
  }
  
  ///// Grey Tile /////
  public void displayOldTile(int x, int y, int count){
    int[][][] frame = m.getFrame();
    for(int i = 0; i < m.getHeight(); i++){
      for(int j = 0; j < m.getWidth(); j++){
        if(count-1 >= 0){
          if(frame[count-1][i][j] == 1){
            fill(200);
            if(theme == "BW"){
              rect(x + j * tileSize, y + i * tileSize, tileSize, tileSize);
            }else{
              ellipse(x + j * tileSize + tileSize/2, y + i * tileSize + tileSize/2, tileSize  * 0.75, tileSize  * 0.75);
            }
          }
        }
      }
    }
  }
  
  private void drawBG(){
    if(theme == "BW"){
      background(255);
    }else{
      background(0);
    }
  }
  
  private void drawTable(int x, int y){
    for(int i = 0; i < m.getHeight(); i++){
      for(int j = 0; j < m.getWidth(); j++){
        noFill();
        if(theme == "BW"){
          stroke(0);
          rect(x + j * tileSize, y + i * tileSize, tileSize, tileSize);
        }else{
          stroke(255);
          ellipse(x + j * tileSize + tileSize/2, y + i * tileSize + tileSize/2, tileSize * 0.75, tileSize  * 0.75);
        }
      }
    }
  }
  
  public int getTileSize(){
    return tileSize;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public String getTheme(){
    return theme;
  }
  
  public String[] getDataTheme(){
    String[] data = {"BW", "RED", "GREEN", "BLUE", "YELLOW"};
    return data;
  }

  public void setTileSize(int tileSize){
    this.tileSize = tileSize;
  }
  
  public void setX(int x){
    this.x = x;
  }
  
  public void setY(int y){
    this.y = y;
  }
  
  public void setTheme(String theme){
    this.theme = theme;
  }
  
}


class APlayer{
  private Amaker m;
  private AViewer v;
  private boolean play;
  private boolean pause;
  private boolean repeat;
  private int animateFrame;
  
  public APlayer(Amaker m, AViewer v){
    this.m = m;
    this.v = v;
    play = false;
    pause = false;
    repeat = false;
    animateFrame = 0;
  }
  
  public void playIt(){
    play = true;
    pause = false;
  }
  
  public void pauseIt(){
    pause = true;
  }
  
  public void stop(){
    play = false;
    animateFrame = 0;
  }
  
  ///// Update for playing ////
  public void update(){
    if(play && !pause){
      if(frameCount % 30 == 0){
        if(animateFrame >= m.getIndexFrame()){
          if(!repeat){
            play = false;
          }
          animateFrame = 0;
        }else{
            animateFrame++;
        }         
      }
    }
  }
  
  public void display(int x, int y){
    v.display(x, y, animateFrame, false);
  }
  
  public boolean isPlay(){
    return play;
  }
  
  public boolean isPause(){
    return pause;
  }
  
  public boolean isRepeat(){
    return repeat;
  }
  
  public int getAniFrame(){
    return animateFrame;
  }
  
  public void setPlay(boolean play){
    this.play = play;
  }
  
  public void setPause(boolean pause){
    this.pause = pause;
  }
  
  public void setRepeat(boolean repeat){
    this.repeat = repeat;
  }
  
}


class AController{
  private Amaker m;
  private AViewer v;
  private APlayer p;
  private String action;
  private int themeNum;
  private String data;
  private int frameSize, wData, hData;
  
  public AController(Amaker m, AViewer v, APlayer p){
    this.m = m;
    this.v = v;
    this.p = p;
    action = "START";
    themeNum = 0;
    frameSize = 0;
    wData = 0;
    hData = 0;
  }
  
  public void update(){
    p.update();
  }
  
  ///// Forward /////
  public void nextFrame(){
    m.setCurrentFrame(m.getCurrentFrame() + 1);
  }
  
  ///// Backward /////
  public void prevFrame(){
    m.setCurrentFrame(m.getCurrentFrame() - 1);
  }
  
  ///// Load from save pressing /////
  public void load(String data, int frameSize, int wData, int hData){
    if(data != null){
      m.load(data, frameSize, wData, hData);
    }
  }
  
  public void mousePressed(){
    if(!p.isPlay()){
      clickTile(v.getX(), v.getY());
    }
    clickController();
  }
  
  public void mouseDragged(){
    if(!p.isPlay()){
      dragTile(v.getX(), v.getY());
    }
  }
  
  private void clickController(){
    ///// Left Button /////
    if(!p.isPlay()){
      if(isClick(0, 0, 0.2 * width, 0.1 * height)){
        m.addFrame();
        action = "Add Frame";
      }else if(isClick(0, 0.1 * height, 0.2 * width, 0.1 * height)){
        m.removeFrame();
        action = "Remove Frame";
      }else if(isClick(0, 0.2 * height, 0.2 * width, 0.1 * height)){
        m.resetFrame();
        action = "Reset Frame";
      }else if(isClick(0, 0.3 * height, 0.2 * width, 0.1 * height)){
        m.addTileAll();
        action = "Add All Tile";
      }else if(isClick(0, 0.4 * height, 0.2 * width, 0.1 * height)){
        themeNum++;
        if(themeNum == v.getDataTheme().length){
          themeNum = 0;
        }
        v.setTheme(v.getDataTheme()[themeNum]);
        action = v.getDataTheme()[themeNum] + " Theme";
      }else if(isClick(0, 0.7 * height, 0.1 * width, 0.1 * height)){
        prevFrame();
        action = "Previous";
      }else if(isClick(0.1 * width, 0.7 * height, 0.1 * width, 0.1 * height)){
        nextFrame();
        action = "Next";
      }else if(isClick(0, 0.5 * height, 0.1 * width, 0.1 * height)){
        if((m.getWidth()+1) * v.getTileSize() <= 0.8 * width)
          m.addWidth();
        action = "Add Width";
      }else if(isClick(0.1 * width, 0.5 * height, 0.1 * width, 0.1 * height)){
        if((m.getWidth()-1) * v.getTileSize() > 0)
          m.removeWidth();
        action = "Remove Width";
      }else if(isClick(0, 0.6 * height, 0.1 * width, 0.1 * height)){
        if((m.getHeight()+1) * v.getTileSize() <= 0.8 * height)
          m.addHeight();
        action = "Add Height";
      }else if(isClick(0.1 * width, 0.6 * height, 0.1 * width, 0.1 * height)){
        if((m.getHeight()-1) * v.getTileSize() > 0)
          m.removeHeight();
        action = "Remove Height";
      }
    }
    ///// Bottom Button /////
    if(isClick(0 * width, 0.8 * height, 0.1 * width, 0.2 * height)){
      action = "Play";
      p.playIt();
    }else if(isClick(0.1 * width, 0.8 * height, 0.1 * width, 0.2 * height)){
      action = "Pause";
      p.pauseIt();
    }else if(isClick(0.2 * width, 0.8 * height, 0.1 * width, 0.2 * height)){
      action = "Stop";
      p.stop();
    }else if(isClick(0.3 * width, 0.8 * height, 0.1 * width, 0.2 * height)){
      p.setRepeat(!p.isRepeat());
      if(p.isRepeat()){
        action = "Repeat";
      }else{
        action = "Not Repeat";
      }
    }else if(isClick(0.5 * width, 0.8 * height, 0.1 * width, 0.2 * height)){
      frameSize = m.getIndexFrame();
      wData = m.getWidth();
      hData = m.getHeight();
      data = m.save();
      action = "Save";
    }else if(isClick(0.6 * width, 0.8 * height, 0.1 * width, 0.2 * height)){
      if(data != null){
        m.load(data, frameSize + 1, wData, hData);
      }
      action = "Load";
    }
  }
  
  ///// Dragged and Tile /////
  private void dragTile(int x, int y){
    int[][][] frame = m.getFrame();
    for(int i = 0; i < m.getHeight(); i++){
      for(int j = 0; j < m.getWidth(); j++){
        if(mouseButton == LEFT){
          if(isClick(x + j * v.getTileSize() , y + i * v.getTileSize(), v.getTileSize(), v.getTileSize())){
            if(frame[m.getCurrentFrame()][i][j] == 0){
              m.addTile(j+1, i+1);
              action = "Add Tile";
            }
          }
        }else if(mouseButton == RIGHT){
          if(isClick(x + j * v.getTileSize() , y + i * v.getTileSize(), v.getTileSize(), v.getTileSize())){
            if(frame[m.getCurrentFrame()][i][j] == 1 || frame[m.getCurrentFrame()][i][j] == 2){
              ///// remove tile /////
              m.removeTile(j+1, i+1);
              ///// Check tile ready to move -> path/////
              if(j-1 >= 0)
                if(frame[m.getCurrentFrame()][i][j-1] == 3)
                  m.setMoveTile(j, i+1);
              if(j+1 < m.getWidth())
                if(frame[m.getCurrentFrame()][i][j+1] == 3)
                  m.setMoveTile(j+2, i+1);
              if(i-1 >= 0)
                if(frame[m.getCurrentFrame()][i-1][j] == 3)
                  m.setMoveTile(j+1, i);
              if(i+1 < m.getHeight())
                if(frame[m.getCurrentFrame()][i+1][j] == 3)
                  m.setMoveTile(j+1, i+2);
              action = "Remove Tile";
            }else if(frame[m.getCurrentFrame()][i][j] == 3){
              ///// remove path /////
              m.removeTile(j+1, i+1);
              m.unsetMoveTile();
              action = "Remove Tile";
            }
          }
        }
      }
    }
  }
  
  private void clickTile(int x, int y){
    int[][][] frame = m.getFrame();
    for(int i = 0; i < m.getHeight(); i++){
      for(int j = 0; j < m.getWidth(); j++){
        if(mouseButton == LEFT){
          if(isClick(x + j * v.getTileSize() , y + i * v.getTileSize(), v.getTileSize(), v.getTileSize())){
            if(frame[m.getCurrentFrame()][i][j] == 0){
              m.addTile(j+1, i+1);
              action = "Add Tile";
            }else if(frame[m.getCurrentFrame()][i][j] == 1){
              ///// Move Tile /////
              m.setMoveTile(j+1, i+1);
            }else if(frame[m.getCurrentFrame()][i][j] == 3){
              ///// Delete Path /////
              m.unsetMoveTile();
            }else if(frame[m.getCurrentFrame()][i][j] == 4){
              action = "Move Tile";
              if(j-1 >= 0)
                if(frame[m.getCurrentFrame()][i][j-1] == 3)
                  m.moveTile(j, i+1, j+1, i+1);
              if(j+1 < m.getWidth())
                if(frame[m.getCurrentFrame()][i][j+1] == 3)
                  m.moveTile(j+2, i+1, j+1, i+1);
              if(i-1 >= 0)
                if(frame[m.getCurrentFrame()][i-1][j] == 3)
                  m.moveTile(j+1, i, j+1, i+1);
              if(i+1 < m.getHeight())
                if(frame[m.getCurrentFrame()][i+1][j] == 3)
                  m.moveTile(j+1, i+2, j+1, i+1);
            }
          }
        }else if(mouseButton == RIGHT){
          if(isClick(x + j * v.getTileSize() , y + i * v.getTileSize(), v.getTileSize(), v.getTileSize())){
            if(frame[m.getCurrentFrame()][i][j] == 1 || frame[m.getCurrentFrame()][i][j] == 2){
              ///// remove tile /////
              m.removeTile(j+1, i+1);
              ///// Check tile ready to move -> path/////
              if(j-1 >= 0)
                if(frame[m.getCurrentFrame()][i][j-1] == 3)
                  m.setMoveTile(j, i+1);
              if(j+1 < m.getWidth())
                if(frame[m.getCurrentFrame()][i][j+1] == 3)
                  m.setMoveTile(j+2, i+1);
              if(i-1 >= 0)
                if(frame[m.getCurrentFrame()][i-1][j] == 3)
                  m.setMoveTile(j+1, i);
              if(i+1 < m.getHeight())
                if(frame[m.getCurrentFrame()][i+1][j] == 3)
                  m.setMoveTile(j+1, i+2);
              action = "Remove Tile";
            }else if(frame[m.getCurrentFrame()][i][j] == 3){
              ///// remove tile /////
              m.removeTile(j+1, i+1);
              m.unsetMoveTile();
              action = "Remove Tile";
            }
          }
        }
      }
    }
  }
 
  public void display(){
    if(!p.isPlay()){
      ///// not play -> showTile /////
      displayTile(v.getX(), v.getY());
    }else{
      ///// play -> player do it /////
      p.display(v.getX(), v.getY());
    }
    displayMouseController();
  }
  
  private void displayMouseController(){
    ///// Button Background/////
    noStroke();
    fill(#02A8F4);
    rect(0, 0, 0.2 * width, height);
    rect(0, 0.8 * height, width, 0.2 * height);
    
    ///// Pressed Buttton Background/////
    fill(#3E50B4);
    if(p.isPlay() && !p.isPause()){
      rect(0, 0.8 * height, 0.1 * width, 0.2 * height);
    }
    if(p.isPause() && p.isPlay()){
      rect(0.1 * width, 0.8 * height, 0.1 * width, 0.2 * height);
    }
    if(p.isRepeat()){
      rect(0.3 * width, 0.8 * height, 0.1 * width, 0.2 * height);
    }
    
    ///// Button Text /////
    fill(255);
    textAlign(CENTER, CENTER);
    text("Add Frame", 0.1 * width, 0.05 * height);
    text("Remove Frame", 0.1 * width, 0.15 * height);
    text("Reset Frame", 0.1 * width, 0.25 * height);
    text("Add All Tile", 0.1 * width, 0.35 * height);
    text("Theme", 0.1 * width, 0.45 * height);
    text("+\nWidth", 0.05 * width, 0.55 * height);
    text("-\nWidth", 0.15 * width, 0.55 * height);
    text("+\nHeight", 0.05 * width, 0.65 * height);
    text("-\nHeight", 0.15 * width, 0.65 * height);
    text("Previous", 0.05 * width, 0.75 * height);
    text("Next", 0.15 * width, 0.75 * height);
    
    text("Play", 0.05 * width, 0.9 * height);
    text("Pause", 0.15 * width, 0.9 * height);
    text("Stop", 0.25 * width, 0.9 * height);
    text("Repeat", 0.35 * width, 0.9 * height);
    text("Save", 0.55 * width, 0.9 * height);
    text("Load", 0.65 * width, 0.9 * height);
    
    ///// Frame Text /////
    if(p.isPlay()){
      text((p.getAniFrame()+1) + "/" + (m.getIndexFrame()+1), 0.45 * width, 0.9 * height);
    }else{
      text((m.getCurrentFrame()+1) + "/" + (m.getIndexFrame()+1), 0.45 * width, 0.9 * height);
    }
    
    ///// Right Text /////
    textAlign(LEFT, CENTER);
    text("W : " + m.getWidth() + " H : " + m.getHeight() + " Tilesize : " + v.getTileSize(),
      0.75 * width, 0.85 * height);
    text("Nuber of tile : " + m.getTotalNumTile(), 0.75 * width, 0.9 * height);
    text(action, 0.75 * width, 0.95 * height);
  }
  
  private void displayTile(int x, int y){
    if(!p.isPlay()){
      v.displayOldTile(x, y, m.getCurrentFrame());
      v.display(x, y, m.getCurrentFrame(), true);
      displayMouseOn(x, y, m.getCurrentFrame());
    }
  }
  
  ///// When mouse on it /////
  private void displayMouseOn(int x, int y, int count){    
    int[][][] frame = m.getFrame();
    for(int i = 0; i < m.getHeight(); i++){
      for(int j = 0; j < m.getWidth(); j++){
        if(isClick(x + j * v.getTileSize() , y + i * v.getTileSize(), v.getTileSize(), v.getTileSize())){
          if(frame[count][i][j] == 1){
            ///// Click on tile can move /////
            ///// Color /////
            if(v.getTheme() == "GREEN"){
              fill(#019587);
            }else{
              fill(#4BAF4F);
            }
            ///// Style /////
            if(v.getTheme() == "BW"){
              rect(x + j * v.getTileSize(), y + i * v.getTileSize(), v.getTileSize(), v.getTileSize());
            }else{
              ellipse(x + j * v.getTileSize() + v.getTileSize()/2,
                y + i * v.getTileSize() + v.getTileSize()/2,
                v.getTileSize() * 0.75,
                v.getTileSize()  * 0.75);
            }
          }else if(frame[count][i][j] == 2){
            ///// Click on tile can't move /////
            ///// Color /////
            if(v.getTheme() == "RED"){
              fill(#E91D62);
            }else{
              fill(#F44236);
            }
            ///// Sytle /////
            if(v.getTheme() == "BW"){
              rect(x + j * v.getTileSize(), y + i * v.getTileSize(), v.getTileSize(), v.getTileSize());
            }else{
              ellipse(x + j * v.getTileSize() + v.getTileSize()/2,
                y + i * v.getTileSize() + v.getTileSize()/2,
                v.getTileSize() * 0.75,
                v.getTileSize()  * 0.75);
            }
          }
        }
      }
    }
  }
  
  boolean isClick(float x, float y, float w, float h){
    if(mouseX >= x && mouseY >= y && mouseX < x+w && mouseY < y+h)
      return true;
    return false;
  }
  
  boolean isClickRadius(float x, float y, float r){
    if(dist(mouseX, mouseY, x, y) < r/2)
      return true;
    return false;
  }
}

///// Function /////
AController c;
int time;

void setup(){
  size(720, 480);
  frameRate(60);
  Amaker m = new Amaker(18, 10);
  AViewer v = new AViewer(m);
  APlayer p = new APlayer(m, v);
  v.setTileSize(32);
  v.setX(int(0.2 * width));
  v.setY(0);
  c = new AController(m, v, p);
  //c.load(data, frameSize, wData, hData);
}

void draw(){
  c.display();
  c.update();
}

void mousePressed(){
  c.mousePressed();
}

void mouseDragged(){
  c.mouseDragged();
}