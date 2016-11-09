/*
//���������
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
//ͼ�ο����
#include <glut.h>

//ȫ�ֱ任��������
float fTranslate;
float fRotate;
float fScale = 1.0f;

//��ʾģʽ
bool bAnim = false;		//�Զ���ת
bool bWire = false;		//�߿�ģ��
bool bPersp = false;	//͸����ʾ

//���ڴ�С
int wHeight = 0;
int wWidth = 0;

//��ת����
int ftRotate = 0;	//�����ת�Ƕ�-�Ƕ���
int feRotateH = 0;	//ˮƽ�ӽ���ת�Ƕ�-�Ƕ���
int feRotateV = 0;	//��ֱ�ӽ���ת�Ƕ�-�Ƕ���
int fvRotateH = 0;	//ˮƽ�ӽ�ת���Ƕ�-�Ƕ���
int fvRotateV = 0;	//��ֱ�ӽ�ת���Ƕ�-�Ƕ���

//�������
float eye[] = { 0, 0, 8 };			//�ӽ�����
float center[] = { 0, 0, 0 };		//���յ�����
float head[] = { 0, 1, 0};			//ͷ������
float tTranslate[] = { 0, 0, 0 };	//���ƫ������

//�������
float r = 8;		//�ӽ�����յ�֮�����-�����ӽǵ���ת
//��������
const double PI = 4 * atan(1);	//PI
								//�ӽ���ת����
const int rv_Left = 0; const int rv_Right = 1; const int rv_Up = 2; const int rv_Down = 3;
//�ӽ��ƶ�����
const int tv_Left = 0; const int tv_Right = 1; const int tv_Up = 2; const int tv_Down = 3;
//�����ת
const int rt_Left = 0; const int rt_Right = 1;
//�����ӽǺͲ��յ�֮�����
void updateR()
{
	r = sqrt(pow(eye[0] - center[0], 2) + pow(eye[1] - center[1], 2) + pow(eye[2] - center[2], 2));
}
//���ƹ۲�����ת
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
//���ƹ۲�����ת
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
//���ƹ۲����ƶ�
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
//�ƶ��ӽ�
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
//ʵ����ƺ���
void Draw_Scene()
{
	//����ƫ��������
	float ftTranslate[][3] = {
		{ 0, 0, 3.5 },
		{ 1.5, 1, 1.5 },
		{ -1.5, 1, 1.5 },
		{ 1.5, -1, 1.5 },
		{ -1.5, -1, 1.5 }
	};
	//��������������
	float ftScale[][3] = {
		{ 5, 4, 1 },
		{ 1, 1, 3 }
	};
	//����Ļ���
	glPushMatrix();
	//ȷ������Ļ���λ��
	glTranslatef(0 + tTranslate[0], 0 + tTranslate[1], 4 + 1 + tTranslate[2]);
	glRotatef(90, 1, 0, 0); 		//�� X ����ת�������ͷ����
	glRotatef(ftRotate, 0, 1, 0);	//�� Y ����ת�����ʵ��Ҫ��

	glutSolidTeapot(1);
	glPopMatrix();
	//���ӵĻ���
	for (int ri = 0; ri < 5; ri++)
	{
		glPushMatrix();
		glTranslatef(ftTranslate[ri][0], ftTranslate[ri][1], ftTranslate[ri][2]);
		glScalef(ftScale[(ri >= 1)][0], ftScale[(ri >= 1)][1], ftScale[(ri >= 1)][2]);
		glutSolidCube(1.0);
		glPopMatrix();
	}
}
//�Ӵ��ĸ���
void updateView(int width, int height)
{
	glViewport(0, 0, width, height);	//�����Ӵ�

	glMatrixMode(GL_PROJECTION);		//�����Ӵ��ı任
	glLoadIdentity();					//��ʼ���Ӵ�����

	float whRatio = (GLfloat)width / (GLfloat)height;	//�����ݺ��

	//ѡ���Ӵ�����
	if (bPersp)
		gluPerspective(90, whRatio, 1, 100);	//͸���ӽ�
	else
		glOrtho(-3, 3, -3, 3, -100, 100);		//��ֱͶӰ�ӽ�

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
		case 'p': bPersp = !bPersp; updateView(wHeight, wWidth); break; //�ӽ�ģʽ
		case ' ': bAnim = !bAnim; break;	//�Զ���ת
		case 'o': bWire = !bWire; break;	//��ʾģʽ
		//��ת�ӽ�
		case 'f': feRotateH--; rotateViewV2(); break;
			//rotateViewV1(rv_Left);	break;
		case 'h': feRotateH++; rotateViewV2(); break;
			//rotateViewV1(rv_Right);	break;
		case 't': feRotateV++; rotateViewV2(); break;
			//rotateViewV1(rv_Up);	break;
		case 'g': feRotateV--; rotateViewV2(); break;
			//rotateViewV1(rv_Down);	break;

		//���߶� ������� Ctrl ������Ʋ�����ƶ�
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
		//�ƶ��ӽ�
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

	//��ʾģ��ѡ�񣬿�ѡ�߿�ͼ��ʵ��ͼ
	if (bWire) {
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	}
	else {
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}

	//�ƹ⴦��
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