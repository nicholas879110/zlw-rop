/**
 * 版权声明： 版权所有 违者必究 2012
 * 日    期：12-6-5
 */
package com.rop.config;

import com.rop.event.RopEventListener;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class RopEventListenerHodler {

    @SuppressWarnings("rawtypes")
	private RopEventListener ropEventListener;

    @SuppressWarnings("rawtypes")
	public RopEventListenerHodler(RopEventListener ropEventListener) {
        this.ropEventListener = ropEventListener;
    }

    @SuppressWarnings("rawtypes")
	public RopEventListener getRopEventListener() {
        return ropEventListener;
    }
}

