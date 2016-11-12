package itemBasedCF;

import java.util.ArrayList;
/**
 * an item-based model for recommendation using collaborative filtering 
 * @author Wang Menghan
 * @version 1.0 
 * @Time 2015-11-4 15:48:39
 */
public class ItemBasedModel {
	 private ArrayList<String>users;
	 private ArrayList<String>items;
	 private double [][]uiRating;
	 private double [][]similarityResult;
	 
	 //similarityMethod constants
	 final static  int CORSIM =1;
	 final static int ADJCONSIM=2;
	 
	 //
	 public ItemBasedModel(){
		 // an easy version of initialization
		 users = new ArrayList<String>(); 
		 users.add("Jim");
		 users.add("Alice");
		 users.add("Sally");
		 users.add("Kevin");
		 users.add("Tommy");
		 users.add("George");
		 users.add("Joseph");
		 
		 items = new ArrayList<String>();
		 items.add("beef");
		 items.add("sausage");
		 items.add("mutton");
		 items.add("pork");
		 items.add("chicken");
		 
		 double[][]uiRating_matrix ={
				 {4,4,0,3,3},
				 {4,0,0,3,3},
				 {4,0,0,1,1},
				 {1,1,1,2,0},
				 {2,2,2,0,0},
				 {1,1,1,0,0},
				 {5,5,5,0,0}
		 };
		 uiRating = uiRating_matrix;
	 }
	 public void buildModel(int similarityMethod) {	
		 if(similarityMethod == CORSIM){
			 CorSimilarityMethod corSimilarityMethod = new CorSimilarityMethod();
			 similarityResult = corSimilarityMethod.calSimilarity(uiRating);
		 }else if(similarityMethod == ADJCONSIM){
			 //TO-DO, your code here
			 
		 }else{
			 System.out.println("warning: no such similarityMethod !");
			 return;
		 }
		 
		 
		 //print the similarity
		 for (int i =0;i<similarityResult.length;i++){
			 for (int j =0;j<similarityResult.length;j++){
				 System.out.print(similarityResult[i][j]);
				 System.out.print(" ");
			 }
			 System.out.println(" ");
		 }
		
	    }
	 //input the userId and itemId, return the predicted score
	 public double predict(int uid,int iid){
		 double simTotal =0.0, ratingSimToal=0.0;
		 for(int i=0;i<uiRating[0].length;i++){
			 if(i!=iid && uiRating[uid][i]>0){
				 simTotal+=similarityResult[i][iid];
				 ratingSimToal+= similarityResult[i][iid] * uiRating[uid][i];
			 }
		 }
		 double predictedscore =0.0;
		 if (simTotal>0)
			 predictedscore= ratingSimToal/simTotal;
		 System.out.println(users.get(uid)+" to "+items.get(iid)+": predictedscore "+predictedscore);
		 return predictedscore;
	 }
	 
	 // input the username and return the predicted scores for his unrated items. used the weighted sum method.
	 public void predict(String userName){
		 if(users.contains(userName)){
			 int uid = users.indexOf(userName);
			 for(int j =0;j<uiRating[0].length;j++){
				 if(uiRating[uid][j]==0.0){
					 predict(uid,j);
				 }
			 }
		 }else{
			 System.out.println("warning: no such user named "+userName+" in the dataset");
		 }
	 }
	 
	 
	 public static void main(String[] args) {
		 ItemBasedModel test = new ItemBasedModel();
		 test.buildModel(CORSIM);
		 //testing
		 test.predict("Sally");
		 test.predict("Tommy");
		 test.predict("Curry");
		 
		 //after completing the AdjCosineSimilarityMethod, you can test it like the following codes.
		 /*
		 ItemBasedModel test1 = new ItemBasedModel();
		 test1.buildModel(ADJCONSIM);
		 test1.predict("Sally");
		 test1.predict("Tommy");
		 test1.predict("Curry");
		 */
	 }
}