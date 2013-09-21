package controllers.utils;

public class BoundingBox 
{
	private static final float CONVERSIONDISTANCE = 0.0000040f ;
	
	private float left;
	private float bottom;
	private float right;
	private float top;
	
	public BoundingBox(float latitude, float longitude, long distance)
	{
		float distDegree = distance * CONVERSIONDISTANCE ;
		System.out.println("[distance] " + distDegree);
		left = longitude - distDegree;
		bottom = latitude - distDegree ;
		right = longitude + distDegree ;
		top = longitude - distDegree ;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}
}
