package cl.ryc.forfimi.error;

/**
 * Created by RyC on 07/04/2016.
 */

public class ErrorHandler
{

    final static int NoError=0;
    final static int ConnectionError=1;
    final static int JsonProblem=2;
    final static int JsonEmpty=3;
    final static int unknownError=100;
    final static int DifferentPass=101;
    final static int AllFieldsEmpty=102;
    final static int SomeFieldsEmpty=103;
    final static int JsonNotInterpreted=11;
    final static int JsonParserError=300;



    private static int LastError;




    public ErrorHandler()
    {

    }

    private static ErrorHandler instance;



    public static ErrorHandler getInstance()
    {
        if(instance==null)
        {
            instance=new ErrorHandler();
        }


        return instance;
    }

    public static int getLastError()
    {
        return LastError;
    }


    public static void setLastError( int Error)
    {
        LastError=Error;
    }

}

