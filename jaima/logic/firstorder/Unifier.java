package jaima.logic.firstorder;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import jaima.logic.firstorder.parsing.FOLParser;
import jaima.logic.firstorder.parsing.ast.FOLNode;
import jaima.logic.firstorder.parsing.ast.Function;
import jaima.logic.firstorder.parsing.ast.Predicate;
import jaima.logic.firstorder.parsing.ast.Variable;

/**
 * @author Ravi Mohan
 * 
 */

public class Unifier {

//	private FOLParser parser;

	public Unifier(FOLParser parser) {

//		this.parser = parser;
	}

	// returns a Hashtable of results if succesful , null if fail
	public Hashtable<FOLNode, FOLNode> unify(
			FOLNode x, FOLNode y, Hashtable<FOLNode, FOLNode> theta) {

		if (theta == null) {
			return null;
		} else if (x.equals(y)) {
			return theta;
		} else if (isVariable(x)) {
			return unifyVar((Variable) x, y, theta);
		} else if (isVariable(y)) {
			return unifyVar((Variable) y, x, theta);
		} else if ((isCompound(x)) && (isCompound(y))) {
			return unifyLists(args(x), args(y), unifyOps(op(x), op(y), theta));
		} else if (isList(x) && isList(y)) {
			return unifyLists((List<FOLNode>) x, (List<FOLNode>) y, theta);
		}

		else {
			return null;
		}
	}

	public Hashtable<FOLNode, FOLNode> unifyLists(
			List<FOLNode> x, List<FOLNode> y, Hashtable<FOLNode, FOLNode> theta) {
		if (theta == null) {
			return null;
		} else if (x.equals(y)) {
			return theta;
		} else {
			return unifyLists(rest(x), rest(y),
					unify(first(x), first(y), theta));
		}
	}

	public Hashtable<FOLNode, FOLNode> unifyOps(
			String x, String y, Hashtable<FOLNode, FOLNode> theta) {
		if (theta == null) {
			return null;
		} else if (x.equals(y)) {
			return theta;
		} else {

			return null;
		}
	}

	private Hashtable<FOLNode, FOLNode> unifyVar(
			Variable var, FOLNode x, Hashtable<FOLNode, FOLNode> theta) {
		if (theta.keySet().contains(var)) {

			return unify((FOLNode) theta.get(var), x, theta);
		} else if (theta.keySet().contains(x)) {

			return unify(var, (FOLNode) theta.get(var), theta);
		} else if (occurCheck(var, x)) {
			return null;// failure
		} else {
			theta.put(var, x);
			return theta;
		}
	}

	private boolean occurCheck(Variable var, FOLNode x) {
		return false;
	}

	private List<FOLNode> args(FOLNode x) {
		if (isFunction(x)) {
			return new ArrayList<FOLNode>(((Function) x).getTerms());
		} else if (isPredicate(x)) {
			return new ArrayList<FOLNode>(((Predicate) x).getTerms());
		} else {
			return null;
		}

	}

	private String op(FOLNode x) {
		if (isFunction(x)) {
			return ((Function) x).getFunctionName();
		} else if (isPredicate(x)) {
			return ((Predicate) x).getPredicateName();
		} else {
			return null;
		}

	}

	private boolean isList(FOLNode x) {
		return List.class.isInstance(x);
	}

	private boolean isVariable(FOLNode x) {
		return Variable.class.isInstance(x);
	}

	private boolean isPredicate(FOLNode x) {
		return Predicate.class.isInstance(x);
	}

	private boolean isFunction(FOLNode x) {
		return Function.class.isInstance(x);
	}

	private FOLNode first(List<FOLNode> x) {
		List<?> other = duplicate(x);
		FOLNode first = (FOLNode) other.get(0);
		return first;
	}

	private List<FOLNode> rest(List<FOLNode> x) {
		if (x.size() == 1) {
			return new ArrayList<FOLNode>();
		} else {
			List<FOLNode> other = duplicate(x);
			other.remove(0);
			return other;
		}

	}

	private List<FOLNode> duplicate(List<FOLNode> x) {
		List<FOLNode> other = new ArrayList<FOLNode>();
		Iterator<FOLNode> iter = x.iterator();
		while (iter.hasNext()) {
			other.add(iter.next());
		}
		return other;
	}

	private boolean isCompound(FOLNode x) {
		if (isPredicate(x) || isFunction(x)) {
			return true;
		} else {
			return false;
		}
	}

}
