

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionPermutation {

	public enum Direction {
		LEFT,
		RIGHT
	}

	List<Permutation> permutations;

	public CollectionPermutation() {
		this.permutations = new ArrayList<Permutation>();
	}

	public CollectionPermutation(List<Permutation> permutations) {
		this.permutations = permutations;
	}

	public void addPermutation(Permutation permutation) {
		this.permutations.add(permutation);
	}

	public void addPermutationNoDouble(Permutation permutation) {
		if (!this.containsPermutation(permutation))
			this.permutations.add(permutation);
	}

	public Permutation getEntry(int i) {
		return this.permutations.get(i);
	}

	public int getSize() {
		return this.permutations.size();
	}

	public CollectionPermutation link(Permutation other, Direction dir) {
		CollectionPermutation result = new CollectionPermutation();

		for (int i = 0; i < this.permutations.size(); i++) {
			Permutation temp;
			if (dir == Direction.LEFT)
				temp = other.link(this.permutations.get(i));
			else
				temp = this.permutations.get(i).link(other);

			if (!result.containsPermutation(temp))
				result.addPermutation(temp);
		}

		return result;
	}

	public boolean isAbelsch() {
		for (int i = 0; i < this.permutations.size(); i++) {
			Permutation p1 = this.permutations.get(i);
			for (int j = i; j < this.permutations.size(); j++) {
				Permutation p2 = this.permutations.get(j);
				Permutation lP = p1.link(p2);
				Permutation rP = p2.link(p1);
				if (!lP.equals(rP))
					return false;
			}
		}
		return true;
	}

	public boolean isUnderGroup() {
		boolean completed = checkCompletion();
		boolean neutral = checkNeutral();
		boolean invers = checkInversion();
		return completed & neutral & invers;
	}

	private boolean checkCompletion() {
		for (int i = 0; i < this.permutations.size(); i++) {
			Permutation p1 = this.permutations.get(i);
			for (int j = 0; j < this.permutations.size(); j++) {
				Permutation p2 = this.permutations.get(j);
				Permutation linkedP = p1.link(p2);
				if (!containsPermutation(linkedP))
					return false;
			}
		}
		return true;
	}

	private boolean checkNeutral() {
		for (int i = 0; i < this.permutations.size(); i++) {
			Permutation p1 = this.permutations.get(i);
			boolean isNeutral = false;
			for (int j = 0; j < this.permutations.size(); j++) {
				Permutation p2 = this.permutations.get(j);
				Permutation linkedP = p1.link(p2);
				isNeutral = linkedP.equals(p2);
			}
			if (isNeutral)
				return true;
		}
		return false;
	}

	private boolean checkInversion() {
		for (int i = 0; i < this.permutations.size(); i++) {
			Permutation p1 = this.permutations.get(i);
			boolean hasInverse = false;
			for (int j = 0; j < this.permutations.size(); j++) {
				Permutation p2 = this.permutations.get(j);
				Permutation linkedP = p1.link(p2);
				//if (!linkedP.equalsNeutral()) {	<- Why is this like that? 
				if (linkedP.equalsNeutral()) {
					hasInverse = true;
					break;
				}
			}
			if (!hasInverse)
				return false;
		}
		return true;
	}

	public Permutation getInversion(Permutation permutation) {
		for (int j = 0; j < this.permutations.size(); j++) {
			Permutation inverse = this.permutations.get(j);
			Permutation linkedP = permutation.link(inverse);
			if (linkedP.equalsNeutral())
				return inverse;
		}

		return null;
	}

	public boolean containsPermutation(Permutation other) {
		for (int j = 0; j < this.permutations.size(); j++) {
			if (other.equals(this.permutations.get(j)))
				return true;
		}

		return false;
	}

	public void printPermutations() {
		for (int i = 0; i < permutations.size(); i++) {
			permutations.get(i).printPermutation();
		}
	}


	public List<CollectionPermutation> getConjugationGroups() {
		HashMap<Permutation, Permutation> permutationInverse = getInverseCouples();

		List<CollectionPermutation> conjugationClasses = new ArrayList<CollectionPermutation>();
		for (Permutation h1 : this.permutations) {
			for (HashMap.Entry<Permutation, Permutation> entry : permutationInverse.entrySet()) {
				Permutation h2 = entry.getKey().link(h1).link(entry.getValue());

				boolean foundClass = addToConjucationClass(h1, h2, conjugationClasses);
				if (!foundClass)
					createNewConjugationClass(h1, conjugationClasses);
			}
		}

		return conjugationClasses;
	}

	public HashMap<Permutation, Permutation> getInverseCouples() {
		HashMap<Permutation, Permutation> permuationInverse = new HashMap<Permutation, Permutation>();
		for (Permutation permutation : this.permutations) {
			if (permuationInverse.containsKey(permutation))
				continue;

			Permutation inverse = this.getInversion(permutation);
			if (inverse != null) {
				permuationInverse.put(inverse, permutation);
			}
		}
		return permuationInverse;
	}

	//Adds to an existing conjugation class and return false if no appropriate conjugation class exists
	private boolean addToConjucationClass(Permutation h1, Permutation h2, List<CollectionPermutation> conjugationClasses) {
		for (CollectionPermutation konjugationClass : conjugationClasses) {
			if (konjugationClass.containsPermutation(h2)) {
				if (!h1.equals(h2))
					konjugationClass.addPermutationNoDouble(h1);

				return true;
			} else if (konjugationClass.containsPermutation(h1)) {
				if (!h1.equals(h2))
					konjugationClass.addPermutationNoDouble(h2);

				return true;
			}
		}

		return false;
	}

	private void createNewConjugationClass(Permutation h1, List<CollectionPermutation> conjugationClasses) {
		CollectionPermutation newClass = new CollectionPermutation();
		newClass.addPermutation(h1);
		conjugationClasses.add(newClass);
	}

}
