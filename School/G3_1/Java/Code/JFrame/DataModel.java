class DataModel extends Parent
	implements Observerin {
	String htm;

	public void setData(String m){
		htm = m;
		setChanged();
		notifyObservers();
	}

	public String getData(){
		return htm;
	}
}