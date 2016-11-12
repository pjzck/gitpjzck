package itemBasedCF;

/**
 * Describes the Correlation-based Similarity measure to be used by a ModelBuilder to build an item-based model
 * @author Wang Menghan
 * @version 1.0 
 * @Time 2015-11-4 15:48:39
 */
public class CorSimilarityMethod implements SimilarityMethod {	
	@Override
	public double[][] calSimilarity(double [][]uiRating) {
		// calculate the avg ratings of items.
		int userNum = uiRating.length;
		int itemNum = uiRating[0].length;
		double[][] similarityResult = new double[itemNum][itemNum]; // store the item-item similarity
		
		// calculate the average score for each item, if we meet 0 (means this item is unrated by the user), we ignore it.
		double []avg = new double[itemNum];
		int []nonZeroNum = new int[itemNum];
		for(int i = 0;i<itemNum;i++){
			for(int j = 0;j<userNum;j++){
				avg[i]+=uiRating[j][i];
				if(uiRating[j][i]>0){
					nonZeroNum[i]+=1;
				}
					
			}
		}
		for(int i = 0;i<itemNum;i++){
			avg[i]/=nonZeroNum[i];	
		}
		//for each item-item pair, we calculate the Correlation-based Similarity using the method stated in Badrul Sarwar's paper - 
		//"Item-Based Collaborative Filtering Recommendation Algorithms"
		for(int i = 0;i<itemNum;i++){
			for(int j = i+1;j<itemNum;j++){
				double num = 0.0, den1 = 0.0, den2 = 0.0;
				for(int k=0;k<userNum;k++){					
					double diff1 = 0.0;
					double diff2 = 0.0;
					if(uiRating[k][i]>0 && uiRating[k][j]>0){
						diff1 = uiRating[k][i] - avg[i];
						diff2 = uiRating[k][j] - avg[j];
						num += diff1 * diff2;
			            den1 += diff1 * diff1;
			            den2 += diff2 * diff2;
					}
				}
				double den = Math.sqrt(den1) * Math.sqrt(den2);
		        if (den == 0.0){					// zero case	
		        	similarityResult[i][j]=0.0;
		        	similarityResult[i][j]=0.0;
		        }
		        similarityResult[i][j] = num / den;	// we store the similarities in a symmetric matrix for later convenience
		        similarityResult[j][i] = num / den;
			}
		}
		return similarityResult;
    }

	

}
