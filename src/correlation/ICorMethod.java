package correlation;

import org.apache.commons.lang.StringUtils;
import smile.stat.hypothesis.CorTest;

import static java.lang.Double.NaN;

public interface ICorMethod
{

    static String[] getAllowedNames()
    {
        return new String[]{Pearson.NAME,Spearmen.NAME};
    }

    static boolean isNameLegal(String corType)
    {
        if( StringUtils.isBlank( corType) )
            return false;
        return corType.equalsIgnoreCase(pearsonMethod.name()) || corType.equalsIgnoreCase(spearmanMethod.name());
    }

    double correlationValue(double[] x, double[] y);
    String name();

    ICorMethod spearmanMethod = new ICorMethod.Spearmen();
    ICorMethod pearsonMethod  = new ICorMethod.Pearson();
    ICorMethod defaultMethod  = pearsonMethod;

    static double doCorrelation(String method, double[] x, double[] y)
    {
        switch (method.toLowerCase()) {
            case Pearson.NAME: {
                return pearsonMethod.correlationValue(x, y);
            }
            case Spearmen.NAME: {
                return spearmanMethod.correlationValue(x, y);
            }
            default: {
                return NaN;
            }
        }
    }

    /**
     * Correlation types
     */
    public enum COR_TYPES
    {
        pearson, // by Pearson
        spearmen // by Spearmen
    }

    class Pearson implements ICorMethod
    {
        static final String NAME = "pearson";
        @Override
        public final double correlationValue(double[] x, double[] y)
        {
            CorTest cor = CorTest.pearson(x,y);
            return cor.cor;
        }

        @Override
        public String name()
        {
            return NAME;
        }

    }

    class Spearmen implements ICorMethod
    {

        static final String NAME = "spearman";

        @Override
        public double correlationValue(double[] x, double[] y)
        {
            CorTest cor = CorTest.spearman(x,y);
            return cor.cor;
        }

        @Override
        public String name()
        {
            return NAME;
        }

    }
}
