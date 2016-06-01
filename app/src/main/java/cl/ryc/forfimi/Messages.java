package cl.ryc.forfimi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;
import java.util.List;

import cl.ryc.forfimi.comms.CommsChart;
import cl.ryc.forfimi.entities.Msg;
import cl.ryc.forfimi.error.ErrorHandler;
import cl.ryc.forfimi.helpers.GlobalPersist;
import cl.ryc.forfimi.view.ViewMessages;

/**
 * Created by RyC on 08/04/2016.
 */
public class Messages extends AppCompatActivity {

    static ListView lvNegativos;
    ProgressDialog pd;
    static Context c;
    static ViewMessages vm;
    private static XYPlot plot;
    //static Number[] series1Numbers= new Number[7];
    //static Number[] seri ;//= {1,2,3,3,3,3,3};
    ImageButton Reload;
    Messages a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        this.c=this;
        lvNegativos=(ListView) findViewById(R.id.lvNegativos);
        Reload=(ImageButton) findViewById(R.id.btnReload);
        Reload.setEnabled(false);

        Reload.setOnClickListener(onReload);
        a=this;
        CommsChart cch=new CommsChart(c,this);
        cch.execute("");


    }



    public static void onAsyncBack(List<Msg> Positivos, List<Msg> Negativos, int Error){

        if(Error==0){

            lvNegativos.setAdapter(vm.toListViewNegativos(Negativos));

        }
        else{
            Toast toast = Toast.makeText(c,"Se ha producido un error al obtener tu información", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }


    }

    public  void createChart(Number[] series1Numbers){
        plot = (XYPlot) findViewById(R.id.plot);
        System.out.println("******************************************");
        System.out.println("******************************************");
        System.out.println("******************************************");
        /*for (int cont=0;cont<7;cont++){

            System.out.println(series1Numbers[cont]);
        }*/
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "");
       plot.getBackgroundPaint().setColor(Color.WHITE);

        plot.setBorderStyle(XYPlot.BorderStyle.NONE, null, null);
        plot.getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
        plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        plot.getGraphWidget().getDomainTickLabelPaint().setColor(Color.WHITE);


        // add a new series' to the xyplot:


        // by default, AndroidPlot displays developer guides to aid in laying out your plot.
        // To get rid of them call disableAllMarkup():



        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(c,
                R.xml.line_point_formatter_with_labels);

       /* LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_labels_2);

        // add an "dash" effect to the series2 line:
        series2Format.getLinePaint().setPathEffect(
                new DashPathEffect(new float[] {

                        // always use DP when specifying pixel sizes, to keep things consistent across devices:
                        PixelUtils.dpToPix(20),
                        PixelUtils.dpToPix(15)}, 0));*/

        // just for fun, add some smoothing to the lines:
        // see: http://androidplot.com/smooth-curves-and-androidplot/
        series1Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(7, CatmullRomInterpolator.Type.Centripetal));

        /*series2Format.setInterpolationParams(
                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));*/

        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
        /*plot.addSeries(series2, series2Format);*/

        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);

        // rotate domain labels 45 degrees to make them more compact horizontally:
        plot.getGraphWidget().setDomainLabelOrientation(-100);

    }

    public void  backfromGetData(Number[] ser){


       /* System.out.println("Voy a Imprimir el resultado2");
        for (int cont=0;cont<7;cont++){
            System.out.println(ser[cont]);
        }*/
        //seri=ser;
        ProgressDialog pd;

        System.out.println("test");

        pd= new ProgressDialog(c);
        pd.setMessage("Estamos cargando tus datos");
        GlobalPersist gp= GlobalPersist.getInstance(c);
        ErrorHandler eh=ErrorHandler.getInstance();
        System.out.println("Error nª:"+eh.getLastError());
        if(eh.getLastError()==0) {
            createChart(ser);

            vm = new ViewMessages(this.c, pd, gp.getGlobalPersist("IdUsuario"));
        }
        else{
            System.out.println("pase por aqui");
            pd.dismiss();
            Toast toast = Toast.makeText(c,"Error de Conexion", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
            toast.show();
        }
        Reload.setEnabled(true);
    }

    View.OnClickListener onReload = new View.OnClickListener() {

        public void onClick(View v) {

            CommsChart cch=new CommsChart(c,a);
            cch.execute("");
        }
    };

}
