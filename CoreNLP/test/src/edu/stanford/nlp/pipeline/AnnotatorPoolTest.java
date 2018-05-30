package edu.stanford.nlp.pipeline;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotation;
import edu.stanford.nlp.util.Lazy;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Makes sure that the pool creates new Annotators when the signature properties change
 */
public class AnnotatorPoolTest extends TestCase {

  static class SampleAnnotatorFactory extends Lazy<Annotator> {
    private static final long serialVersionUID = 1L;
    public SampleAnnotatorFactory(Properties props) {}
    @Override
    protected Annotator compute() {
      return new Annotator() {
        @Override
        public void annotate(Annotation annotation) {
          // empty body; we don't actually use it here
        }

        @Override
        public Set<Class<? extends CoreAnnotation>> requirementsSatisfied() {
          // empty body; we don't actually use it here
          return Collections.emptySet();
        }

        @Override
        public Set<Class<? extends CoreAnnotation>> requires() {
          // empty body; we don't actually use it here
          return Collections.emptySet();
        }
      };
    }

    @Override
    public boolean isCache() {
      return false;
    }
  }



  public void testSignature() throws Exception {
    Properties props = new Properties();
    props.setProperty("sample.prop", "v1");
    AnnotatorPool pool = new AnnotatorPool();
    pool.register("sample", props, new SampleAnnotatorFactory(props));
    Annotator a1 = pool.get("sample");
    System.out.println("First annotator: " + a1);
    pool.register("sample", props, new SampleAnnotatorFactory(props));
    Annotator a2 = pool.get("sample");
    System.out.println("Second annotator: " + a2);
    Assert.assertTrue(a1 == a2);

    props.setProperty("sample.prop", "v2");
    pool.register("sample", props, new SampleAnnotatorFactory(props));
    Annotator a3 = pool.get("sample");
    System.out.println("Third annotator: " + a3);
    Assert.assertTrue(a1 != a3);
  }


  /*public void testGlobalCache() throws Exception {
    Properties props = new Properties();
    props.setProperty("sample.prop", "v1");
    AnnotatorPool pool1 = new AnnotatorPool();
    pool1.register("sample", props, new SampleAnnotatorFactory(props));
    Annotator a1 = pool1.get("sample");

    AnnotatorPool pool2 = new AnnotatorPool();
    pool2.register("sample", props, new SampleAnnotatorFactory(props));
    Annotator a2 = pool2.get("sample");

    assertTrue(a1 == a2);
  }*/

}
