/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.nbdemetra.ra.parametric.ui.html;

import ec.nbdemetra.ra.model.RegressionEnum;
import ec.nbdemetra.ra.parametric.specification.ParametricSpecification;
import ec.nbdemetra.ra.timeseries.ComponentMatrix;
import ec.nbdemetra.ra.timeseries.RevisionId;
import ec.tss.html.HtmlStream;
import ec.tss.html.HtmlTag;
import java.io.IOException;

/**
 *
 * @author aresda
 */
public class HtmlSlopeDrift extends ParametricRegressionHtmlElement {

    public HtmlSlopeDrift(ParametricSpecification specification, ComponentMatrix cpMatrix) {
        super(specification, cpMatrix);
    }

    @Override
    protected void writeMain(HtmlStream stream) throws IOException {

        stream.write(HtmlTag.HEADER1, h1, "Estimation of the Slope and Intercept coefficients")
                .newLine();

        Double limit = 1.0;
        for (int i = 0; i < cpMatrix.getRowsLabels().length; i++) {
            RevisionId rev = (RevisionId) cpMatrix.getRowsLabels()[i];

            stream.write(HtmlTag.HEADER2, h2, Integer.toString(i + 1).concat(". Regression model: ").concat("[").concat(rev.getLatestName()).concat("]")
                    .concat(" = ").concat(formatDoubleScientific(cpMatrix.get(rev, RegressionEnum.INTERCEPT_VALUE)))
                    .concat(" + (").concat(formatDoubleScientific(cpMatrix.get(rev, RegressionEnum.SLOPE_VALUE)))
                    .concat(" * ").concat("[").concat(rev.getPreliminaryName()).concat("]").concat(" )")).newLine();

            stream.open(HtmlTag.DIV);
            writeTableRegressionSummary(stream, rev);
            stream.close(HtmlTag.DIV).newLines(1);

            stream.open(HtmlTag.DIV);
            stream.open(HtmlTag.TABLE);
            writeRowHeader(stream);
            writeRowIntercept(stream, rev);
            writeRowRegressor(stream, rev, rev.getPreliminaryName());
            stream.close(HtmlTag.TABLE);
            stream.close(HtmlTag.DIV).newLines(2);

            writeBP(stream, rev);
            writeWhite(stream, rev);
            writeARCH(stream, rev);
            writeJB(stream, rev);

        }

    }
}
