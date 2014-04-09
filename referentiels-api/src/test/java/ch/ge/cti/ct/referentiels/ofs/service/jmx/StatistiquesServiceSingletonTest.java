package ch.ge.cti.ct.referentiels.ofs.service.jmx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class StatistiquesServiceSingletonTest {

    @Test
    public void test() {
	Map<Call, Stat> stats = null;
	stats = StatistiquesServiceSingleton.instance
		.getStatistiques(String.class);
	assertTrue(stats.isEmpty());

	StatistiquesServiceSingleton.instance.registerCall(String.class,
		String.class.getMethods()[0], null, 1);
	assertTrue(stats.keySet().size() == 1);
	final Call call = stats.keySet().iterator().next();
	assertEquals(String.class.getName(), call.getClasse());
	assertEquals(String.class.getMethods()[0].getName(), call.getMethode());

	StatistiquesServiceSingleton.instance.registerCall(String.class,
		String.class.getMethods()[0], null, 1);
	assertTrue(stats.keySet().size() == 1);

	StatistiquesServiceSingleton.instance.registerCall(String.class,
		String.class.getMethods()[1], null, 1);
	assertTrue(stats.keySet().size() == 2);

	StatistiquesServiceSingleton.instance.registerCall(Integer.class,
		String.class.getMethods()[0], null, 1);
	assertTrue(stats.keySet().size() == 2);

	StatistiquesServiceSingleton.instance.reset(Integer.class);
	assertTrue(stats.keySet().size() == 2);

	StatistiquesServiceSingleton.instance.reset(String.class);
	assertTrue(stats.keySet().size() == 0);
    }

}
