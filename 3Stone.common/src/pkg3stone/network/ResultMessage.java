package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import pkg3stone.engine.Result;

/**
 * ResultMessage Class
 *
 * @author Svitlana Myronova
 */
public class ResultMessage {

    private Result result;

    public ResultMessage(Result result) {
        this.result = result;
    }

    /**
     * Result getter
     *
     * @return
     */
    public Result getResult() {
        return this.result;
    }

    /**
     * Write OutputStream
     *
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException
    {
        os.write(this.result.getWhiteScore());
        os.write(this.result.getBlackScore());
    }
    
    /**
     * Read InputStream
     * 
     * @param is
     * @return ResultMessage
     * @throws IOException
     */
    public static ResultMessage read(InputStream is) throws IOException
    {
        int whiteScore = is.read();
        int blackScore = is.read();
        return new ResultMessage( new Result(whiteScore, blackScore) );
    }
}
