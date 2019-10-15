/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3stone.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import pkg3stone.engine.Result;

/**
 *
 * @author Svitlana Myronova
 */
public class ResultMessage {

    private Result result;

    public ResultMessage(Result result) {
        this.result = result;
    }

    /**
     * Returns result
     *
     * @return
     */
    public Result getResult() {
        return this.result;
    }

    public void write(OutputStream os) throws IOException
    {
        os.write(this.result.getWhiteScore());
        os.write(this.result.getBlackScore());
    }
    
    public static ResultMessage read(InputStream is) throws IOException
    {
        int whiteScore = is.read();
        int blackScore = is.read();
        return new ResultMessage( new Result(whiteScore, blackScore) );
    }
}
