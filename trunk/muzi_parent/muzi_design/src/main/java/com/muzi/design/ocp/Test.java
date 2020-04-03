package com.muzi.design.ocp;

/**
 * 开闭原则
 */
public class Test {
    public static void main(String[] args) {
        Muzi muzi = new Muzi();
        muzi.test001(new One());
        muzi.test001(new Two());
    }
}

abstract class Base{
    int type;
    public abstract void draw();
}

class Muzi{
    public void test001(Base base){
        base.draw();
    }
}


class One extends Base{
    @Override
    public void draw() {
        System.out.println("绘制一个四糸乃");
    }
}
class Two extends Base{
    @Override
    public void draw() {
        System.out.println("绘制一个和泉纱雾");
    }
}



