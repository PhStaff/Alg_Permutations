

import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) throws Exception {
		//quickLinking();
		//getSecondaryLeftGroup();
		//checkUnderGroup();
		//checkAbelsch();
		//getConjugationClasses();
		getConjugationClassesAlternateGroup();
		//checkNormalDivider();
	}

	private static void quickLinking() {
		Permutation p1 = new Permutation(new int[] {3, 1, 2});
		Permutation p2 = new Permutation(new int[] {2, 3, 1});
		Permutation p1_2 = new Permutation(new int[] {1, 3, 2});

		Permutation result = p1.link(p1_2).link(p2);

		p1.printPermutation();
		p2.printPermutation();
		result.printPermutation();
	}

	private static void getSecondaryLeftGroup() throws Exception {
		System.out.println("S4/A4-------");
		SymmetricGroup permutationsOf4 = new SymmetricGroup(4);
		CollectionPermutation a4 = permutationsOf4.getAlternatingGroup();
		List<CollectionPermutation> a = permutationsOf4.getLeftSecondaryClass(a4);
		for (int i = 0 ; i < a.size(); i++) {
			CollectionPermutation temp = a.get(i);
			System.out.println("------------");
			temp.printPermutations();
		}

		Permutation p1 = new Permutation(new int[] {2, 3, 1});		//(1 2 3)
		//Permutation p1 = new Permutation(new int[] {2, 1, 3});		//(1 2)
		CyclicGroup cycleGroupd = new CyclicGroup(p1);
		cycleGroupd.printPermutations();

		System.out.println("Index-------");

		SymmetricGroup permutationsOf3 = new SymmetricGroup(3);
		List<CollectionPermutation> secondaryClass = permutationsOf3.getLeftSecondaryClass(cycleGroupd);
		System.out.println(secondaryClass.size());

		System.out.println("------------");
		System.out.println("Secondary C.");

		for (int i = 0; i < secondaryClass.size(); i++) {
			secondaryClass.get(i).printPermutations();
			System.out.println("------------");
		}
	}

	private static void checkUnderGroup() {
		CollectionPermutation collT = new CollectionPermutation();
		collT.addPermutation(new Permutation(new int[] {1, 2, 3}));
		collT.addPermutation(new Permutation(new int[] {2, 3, 1}));
		System.out.println(collT.isUnderGroup());
	}
	
	private static void checkAbelsch() {
		SymmetricGroup permutationsOf3 = new SymmetricGroup(3);
		//permutationsOf3.printPermutations();
		System.out.println(permutationsOf3.getSize());
		System.out.println(permutationsOf3.getAlternatingGroup().getSize());
		System.out.println("S3 abelsch: " + permutationsOf3.isAbelsch());
		System.out.println("A3 abelsch: " + permutationsOf3.getAlternatingGroup().isAbelsch());
	
		System.out.println("------------");
		System.out.println("Alt. G------");
		SymmetricGroup permutationsOf7 = new SymmetricGroup(7);
		System.out.println("A7 abelsch: " + permutationsOf7.getAlternatingGroup().isAbelsch());
	}

	private static void getConjugationClasses() {
		SymmetricGroup permutationsOf5 = new SymmetricGroup(5);

		HashMap<Permutation, Permutation> permuationInverse = permutationsOf5.getInverseCouples();
		System.out.println(permutationsOf5.getSize());

		System.out.println(permuationInverse.size());
		System.out.println("------------");

		List<CollectionPermutation> konjugationClasses = permutationsOf5.getConjugationGroups();

		int counter = 0;
		for (CollectionPermutation konjugationClass : konjugationClasses) {
			counter += konjugationClass.getSize();
			System.out.println(konjugationClass.getSize());
			System.out.println("------------");

			konjugationClass.printPermutations();
			System.out.println("XXXXXXXXXXXX");
			System.out.println("------------");
		}
		System.out.println(counter);
	}

	private static void getConjugationClassesAlternateGroup() {
		SymmetricGroup symmetricGroup = new SymmetricGroup(4);
		CollectionPermutation alternateGroup = symmetricGroup.getAlternatingGroup();
		alternateGroup.printPermutations();

		List<CollectionPermutation> konjugationClasses = alternateGroup.getConjugationGroups();

		int counter = 0;
		for (CollectionPermutation konjugationClass : konjugationClasses) {
			counter += konjugationClass.getSize();
			System.out.println(konjugationClass.getSize());
			System.out.println("------------");

			konjugationClass.printPermutations();
			System.out.println("XXXXXXXXXXXX");
			System.out.println("------------");
		}
		System.out.println(counter);
	}

	private static void checkNormalDivider() {
		SymmetricGroup symmetricGroup = new SymmetricGroup(5);

		for (int i = 0; i < symmetricGroup.getSize(); i++) {
			Permutation p1 = symmetricGroup.getEntry(i);

			boolean isNormalDivider = true;
			for (int j = 0; j < symmetricGroup.getSize(); j++) {
				Permutation p2 = symmetricGroup.getEntry(j);

				Permutation l = p2.link(p1);
				Permutation r = p1.link(p2);

				if (!l.equals(r)) {
					isNormalDivider = false;
					break;
				}
			}

			if (isNormalDivider) {
				p1.printPermutation();
				System.out.println("XXXXXXXXXXXX");
			}
		}
	}

}
