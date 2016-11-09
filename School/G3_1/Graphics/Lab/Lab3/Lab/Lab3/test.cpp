/*
#include<glut.h>
#include<stdio.h>

void display(
	void)
{

	glClear(GL_COLOR_BUFFER_BIT);
	glColor4f(0.2f, 0.4f, 0.6f, 0.0f);
	glRectf(-0.5f, -0.5f, 0.5f, 0.5f);

	glFlush();
}

static int day = 0; //day的变化：从0到365
static int spin = 0; //旋转角度变化：从0到360
void myIdle(
	void)
{
	++day;
	if (day >= 365)
		day = 0;

	printf("%d\n", day);

	glRotatef((spin++) % 360, 0, 0, 1);

	glutPostRedisplay();
}
int main1(int argc, char*argv[])
{
	glutInit(&argc, argv);

	glutInitDisplayMode(GLUT_RGB | GLUT_SINGLE);
	glutInitWindowPosition(100, 100);
	glutInitWindowSize(400, 400);
	glutCreateWindow("helloworld");


	glutDisplayFunc(display);

	glutIdleFunc(&myIdle);

	glutMainLoop();

	return 0;
}
*/