

import java.util.ArrayList;
import java.util.List;

public class SymmetricGroup extends CollectionPermutation {

	int order;

	public SymmetricGroup(int order) {
		this.order = order;

		int[] tempArray = new int[order];
		for (int i = 0; i < order; i++)
			tempArray[i] = 0;

		countUp(tempArray, 0);
	}

	public CollectionPermutation getAlternatingGroup() {
		CollectionPermutation result = new CollectionPermutation();
		for (Permutation permutation : this.permutations) {
			if (permutation.getSignum() == 1)
				result.addPermutation(permutation);
		}
		return result;
	}

	private void countUp(int[] permutation, int index) {
		//Check if index is out of order
		if (permutation.length <= index) {
			this.permutations.add(new Permutation(permutation.clone()));
			return;
		}

		//Count up, until entry is unique or out of order
		for (int i = 1; i <= this.order; i++) {
			permutation[index] = i;
			if (!valueIsUnique(permutation, index, permutation[index]))
				countUp(permutation, index + 1);
		}

		permutation[index] = 0;
	}

	private boolean valueIsUnique(int[] permutation, int index, int value) {
		for (int i = 0; i < permutation.length; i++) {
			if (i == index)
				continue;
			else if (permutation[i] == value)
				return true;
		}

		return false;
	}

	public List<CollectionPermutation> getLeftSecondaryClass(CollectionPermutation group) throws Exception {
		return getSecondaryClass(group, Direction.LEFT);
	}

	public List<CollectionPermutation> getRightSecondaryClass(CollectionPermutation group) throws Exception {
		return getSecondaryClass(group, Direction.RIGHT);
	}

	//Returns a secondary class - a union of collections of permutations
	//(Which should also equal the symmetric group)
	private List<CollectionPermutation> getSecondaryClass(CollectionPermutation underGroup, Direction dir) throws Exception {
		List<CollectionPermutation> secondaryClass = new ArrayList<CollectionPermutation>();
		secondaryClass.add(underGroup);

		for (Permutation permutation : this.permutations) {
			//Check if under group contains permutation
			if (underGroup.containsPermutation(permutation))
				continue;
			//Check if secondary class contains permutation
			if (checkInSeClass(secondaryClass, permutation))
				continue;

			CollectionPermutation collection = new CollectionPermutation();
			for (int j = 0; j < underGroup.getSize(); j++) {
				if (dir == Direction.LEFT)
					collection.addPermutation(permutation.link(underGroup.getEntry(j)));
				else
					collection.addPermutation(underGroup.getEntry(j).link(permutation));
			}
			secondaryClass.add(collection);
		}

		int index = this.getSize() / underGroup.getSize();
		if (index != secondaryClass.size())
			throw new Exception("Index (" + index + ") does not equal secondary class size (" + secondaryClass.size()  + ")");

		return secondaryClass;
	}

	private boolean checkInSeClass(List<CollectionPermutation> secClass, Permutation permutation) {
		for (int j = 0; j < secClass.size(); j++) {
			if (secClass.get(j).containsPermutation(permutation)) {
				return true;
			}
		}

		return false;
	}

}
