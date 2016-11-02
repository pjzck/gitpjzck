## 布局
没有布局的情况下，每个位置上只能放一个控件  
我们需要在局部再放一个容器 一般用 `JPanel` 来作为容器  
布局的五个方向分别是`North`、`South`、`East`、`West`、`Center`  

## 事件
`implements ActionListener` 声明响应  
`import java.awt.event.*` 关联库  
`
btn.addActionListener(this);
public void actionPerformer(ActionEvent e)
{}
`