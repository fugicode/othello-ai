/*
 * Created on Sep 21, 2004
 *
 */
package jaima.search.csp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ravi Mohan
 * 
 */

public class Domain {
	private Hashtable<String, List<String>> variablesToValues;

	// a hash Of Lists { variable: ListOfDomainValues}
	public Domain(List<String> variables) {
		this.variablesToValues = new Hashtable<String, List<String>>();
		Iterator<String> varIter = variables.iterator();
		while (varIter.hasNext()) {
			variablesToValues.put(varIter.next(), new ArrayList<String>());
		}
	}

	public List<String> getDomainOf(String variable) {
		return variablesToValues.get(variable);
	}

	public void add(String variable, String value) {
		List<String> varDomains = variablesToValues.get(variable);

		if (!(varDomains.contains(value))) {
			varDomains.add(value);
		}
	}

	public void addToDomain(String variable, List<String> values) {
		for (int i = 0; i < values.size(); i++) {
			add(variable, values.get(i));
		}

	}

	public void remove(String variable, Object value) {
		List<String> varDomains = variablesToValues.get(variable);
		varDomains.remove(value);
	}

	@Override
	public String toString() {
		return variablesToValues.toString();
	}

}
