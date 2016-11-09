/*
//基础库调用
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
//图形库调用
#include <glut.h>

//全局变换变量定义
float fTranslate;
float fRotate;
float fScale = 1.0f;

//显示模式
bool bAnim = false;		//自动旋转
bool bWire = false;		//线框模型
bool bPersp = false;	//透视显示

//窗口大小
int wHeight = 0;
int wWidth = 0;

//旋转变量
int ftRotate = 0;	//茶壶旋转角度-角度制
int feRotateH = 0;	//水平视角旋转角度-角度制
int feRotateV = 0;	//竖直视角旋转角度-角度制
int fvRotateH = 0;	//水平视角转动角度-角度制
int fvRotateV = 0;	//垂直视角转动角度-角度制

//坐标变量
float eye[] = { 0, 0, 8 };			//视角坐标
float center[] = { 0, 0, 0 };		//参照点坐标
float head[] = { 0, 1, 0};			//头顶朝向
float tTranslate[] = { 0, 0, 0 };	//茶壶偏移坐标

//计算变量
float r = 8;		//视角与参照点之间距离-控制视角的旋转
//常量定义
const double PI = 4 * atan(1);	//PI
								//视角旋转类型
const int rv_Left = 0; const int rv_Right = 1; const int rv_Up = 2; const int rv_Down = 3;
//视角移动类型
const int tv_Left = 0; const int tv_Right = 1; const int tv_Up = 2; const int tv_Down = 3;
//茶壶旋转
const int rt_Left = 0; const int rt_Right = 1;
//更新视角和参照点之间距离
void updateR()
{
	r = sqrt(pow(eye[0] - center[0], 2) + pow(eye[1] - center[1], 2) + pow(eye[2] - center[2], 2));
}
//控制观察点的旋转
void rotateViewV1(int rType) //v1
{
	int VorH = rType / 2;
	int RorS = rType % 2;
	double feRotate0;
	double feRotate1;

	switch (VorH)
	{
		case 0: feRotate0 = feRotateH; feRotateH += (RorS) ? -1 : 1; feRotate1 = feRotateH; break;
		case 1:	feRotate0 = feRotateV; feRotateV += (RorS) ? -1 : 1; feRotate1 = feRotateV; break;
	}
	double d0 = (feRotate0)* PI / 180;
	double d1 = (feRotate1)* PI / 180;

	eye[VorH] += r * (sin(d1) - sin(d0));
	eye[2] += r * (cos(d1) - cos(d0));

	if (feRotateH > 360) feRotateH -= 360;
	if (feRotateH < 0)	feRotateH += 360;
	if (feRotateV > 360) feRotateV -= 360;
	if (feRotateV < 0)	feRotateV += 360;

	printf("H : %d V : %d D : %lf eye[0] : %f eye[1] : %f eye[2] : %f\n", feRotateH, feRotateV, r, eye[0], eye[1], eye[2]);
}
//控制观察点的旋转
void rotateViewV2()
{
	double dH = (feRotateH)* PI / 180;
	double dV = (feRotateV)* PI / 180;

	eye[0] = r * cos(dV) * sin(dH) + center[0];
	eye[1] = r * sin(dV) + center[1];
	eye[2] = r * cos(dV) * cos(dH) + center[2];

	head[0] = -1 * sin(dV) * sin(dH);
	head[1] = cos(dV);
	head[2] = -1 * sin(dV) * cos(dH);

	if (feRotateH > 360) feRotateH -= 360;
	if (feRotateH < 0)	feRotateH += 360;
	if (feRotateV > 360) feRotateV -= 360;
	if (feRotateV < 0)	feRotateV += 360;

	printf("H : %d V : %d D : %lf eye[0] : %f eye[1] : %f eye[2] : %f\n", feRotateH, feRotateV, r, eye[0], eye[1], eye[2]);
}
//控制观察者移动
void moveViewer()
{
	double dH = (feRotateH)* PI / 180;
	double dV = (feRotateV)* PI / 180;

	eye[0] = r * cos(dV) * sin(dH) + center[0];
	eye[1] = r * sin(dV) + center[1];
	eye[2] = r * cos(dV) * cos(dH) + center[2];

	printf("moving people\n");
}
void moveCenter(int RorS)
{
	double dH = (feRotateH)* PI / 180;

	center[0] -= 0.01 * cos(dH) * RorS;
	center[2] += 0.01 * sin(dH) * RorS;

	printf("moving center\n");
}
//移动视角
void moveView()
{
	double dH = (fvRotateH)* PI / 180;
	double dV = (fvRotateV)* PI / 180;

	center[0] = eye[0] + r * (-1) * cos(dV) * sin(dH);
	center[1] = eye[1] + r * sin(dV);
	center[2] = eye[2] + r * (-1) * cos(dV) * cos(dH);

	if (fvRotateH > 360) fvRotateH -= 360;
	if (fvRotateH < 0)	fvRotateH += 360;
	if (fvRotateV > 360) fvRotateV -= 360;
	if (fvRotateV < 0)	fvRotateV += 360;

	printf("H : %d V : %d D : %lf center[0] : %f center[1] : %f center[2] : %f\n", fvRotateH, fvRotateV, r, center[0], center[1], center[2]);
	printf("H : %d V : %d D : %lf eye[0] : %f eye[1] : %f eye[2] : %f\n", feRotateH, feRotateV, r, eye[0], eye[1], eye[2]);
}
//实体绘制函数
void Draw_Scene()
{
	//桌子偏移量定义
	float ftTranslate[][3] = {
		{ 0, 0, 3.5 },
		{ 1.5, 1, 1.5 },
		{ -1.5, 1, 1.5 },
		{ 1.5, -1, 1.5 },
		{ -1.5, -1, 1.5 }
	};
	//桌子缩放量定义
	float ftScale[][3] = {
		{ 5, 4, 1 },
		{ 1, 1, 3 }
	};
	//茶壶的绘制
	glPushMatrix();
	//确定茶壶的绘制位置
	glTranslatef(0 + tTranslate[0], 0 + tTranslate[1], 4 + 1 + tTranslate[2]);
	glRotatef(90, 1, 0, 0); 		//绕 X 轴旋转，将茶壶头朝上
	glRotatef(ftRotate, 0, 1, 0);	//绕 Y 轴旋转，完成实验要求

	glutSolidTeapot(1);
	glPopMatrix();
	//桌子的绘制
	for (int ri = 0; ri < 5; ri++)
	{
		glPushMatrix();
		glTranslatef(ftTranslate[ri][0], ftTranslate[ri][1], ftTranslate[ri][2]);
		glScalef(ftScale[(ri >= 1)][0], ftScale[(ri >= 1)][1], ftScale[(ri >= 1)][2]);
		glutSolidCube(1.0);
		glPopMatrix();
	}
}
//视窗的更新
void updateView(int width, int height)
{
	glViewport(0, 0, width, height);	//重置视窗

	glMatrixMode(GL_PROJECTION);		//进行视窗的变换
	glLoadIdentity();					//初始化视窗矩阵

	float whRatio = (GLfloat)width / (GLfloat)height;	//计算纵横比

	//选择视窗类型
	if (bPersp)
		gluPerspective(90, whRatio, 1, 100);	//透视视角
	else
		glOrtho(-3, 3, -3, 3, -100, 100);		//垂直投影视角

	glMatrixMode(GL_MODELVIEW);
}

void reshape(int width, int height)
{
	if (height == 0)
		height = 1;

	wHeight = height;
	wWidth = width;

	updateView(wHeight, wWidth);
}

void idle()
{
	glutPostRedisplay();
}

void key(unsigned char k, int x, int y)
{
	int mod = glutGetModifiers();
	switch (k)
	{
		case 27: exit(0); break;
		case 'p': bPersp = !bPersp; updateView(wHeight, wWidth); break; //视角模式
		case ' ': bAnim = !bAnim; break;	//自动旋转
		case 'o': bWire = !bWire; break;	//显示模式
		//旋转视角
		case 'f': feRotateH--; rotateViewV2(); break;
			//rotateViewV1(rv_Left);	break;
		case 'h': feRotateH++; rotateViewV2(); break;
			//rotateViewV1(rv_Right);	break;
		case 't': feRotateV++; rotateViewV2(); break;
			//rotateViewV1(rv_Up);	break;
		case 'g': feRotateV--; rotateViewV2(); break;
			//rotateViewV1(rv_Down);	break;

		//人走动 如果按了 Ctrl 键则控制茶壶的移动
		case 'w': 
			if (mod == (GLUT_ACTIVE_ALT)){ tTranslate[1] += 0.01; }
			//else{ eye[2] -= 0.1; updateR(); }
			else { r -= 0.1; moveViewer(); }
			break;
		case 's': 
			if (mod == (GLUT_ACTIVE_ALT)) { tTranslate[1] -= 0.01; }
			//else { eye[2] += 0.01; updateR(); }
			else { r += 0.1; moveViewer(); }
			break;
		case 'a':
			if (mod == (GLUT_ACTIVE_ALT)) { tTranslate[0] -= 0.01; }
			//else { eye[0] -= 0.1; center[0] -= 0.1; } 
			else { moveCenter(1); moveViewer(); }
			break;
		case 'd': 
			if (mod == (GLUT_ACTIVE_ALT)) { tTranslate[0] += 0.01; }
			//else { eye[0] += 0.1; center[0] += 0.1; }
			else { moveCenter(-1); moveViewer(); }
			break;
		case 'q': eye[1] += 0.1; center[1] += 0.1; break;
		case 'e': eye[1] -= 0.1; center[1] -= 0.1; break;
		//移动视角
		case 'i': fvRotateV++; moveView(); break;
		case 'j': fvRotateH--; moveView(); break;
		case 'k': fvRotateV--; moveView(); break;
		case 'l': fvRotateH++; moveView(); break;
	}
}

void redraw()
{

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();

	gluLookAt(eye[0], eye[1], eye[2],
		center[0], center[1], center[2],
		head[0], head[1], head[2]);

	//显示模型选择，可选线框图和实体图
	if (bWire) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	else {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	//灯光处理
	glEnable(GL_DEPTH_TEST);
	glEnable(GL_LIGHTING);
	GLfloat white[] = { 1.0, 1.0, 1.0, 1.0 };
	GLfloat light_pos[] = { 5,5,5,1 };

	glLightfv(GL_LIGHT0, GL_POSITION, light_pos);
	glLightfv(GL_LIGHT0, GL_AMBIENT, white);
	glEnable(GL_LIGHT0);

	//glTranslatef(0.0f, 0.0f,-6.0f);			// Place the triangle at Center
	glRotatef(fRotate, 0, 1.0f, 0);			// Rotate around Y axis
	glRotatef(-90, 1, 0, 0);
	glScalef(0.2, 0.2, 0.2);
	Draw_Scene();

	if (bAnim) fRotate += 0.5f;

	glutSwapBuffers();
}

int main(int argc, char *argv[])
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGBA | GLUT_DEPTH | GLUT_DOUBLE);
	glutInitWindowSize(480, 480);
	int windowHandle = glutCreateWindow("Simple GLUT App");

	glutDisplayFunc(redraw);
	glutReshapeFunc(reshape);
	glutKeyboardFunc(key);
	glutIdleFunc(idle);

	glutMainLoop();
	return 0;
}
*/