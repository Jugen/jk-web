package com.jk.web.faces.exceptions;
/// *
// * Copyright 2002-2016 Jalal Kiswani.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
// package com.jk.web.faces.exceptions;
//
// import java.util.logging.Logger;
//
// import javax.faces.FacesException;
// import javax.faces.context.ExceptionHandler;
// import javax.faces.event.ExceptionQueuedEvent;
//
// import com.jk.annotations.Author;
// import com.jk.exceptions.ExceptionUtil;
//
/// **
// * The Class FacesMessageExceptionHandler.
// *
// * @author Jalal Kiswani
// */
// @Author(name = "Jalal H. Kiswani", date = "1/10/2014", version = "1.0")
// public class FacesMessageExceptionHandler extends
/// org.omnifaces.exceptionhandler.FacesMessageExceptionHandler {
//
// /** The logger. */
// protected Logger logger = Logger.getLogger(getClass().getName());
//
// /**
// * Instantiates a new faces message exception handler.
// *
// * @param wrapped
// * the wrapped
// */
// public FacesMessageExceptionHandler(final ExceptionHandler wrapped) {
// super(wrapped);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see org.omnifaces.exceptionhandler.FacesMessageExceptionHandler#
// * createFatalMessage(java.lang.Throwable)
// */
// @Override
// protected String createFatalMessage(final Throwable exception) {
// return super.createFatalMessage(exception);
// }
//
// /*
// * (non-Javadoc)
// *
// * @see org.omnifaces.exceptionhandler.FacesMessageExceptionHandler#handle()
// */
// @Override
// public void handle() throws FacesException {
// for (final ExceptionQueuedEvent exceptionQueuedEvent :
/// getUnhandledExceptionQueuedEvents()) {
// final Throwable exception = exceptionQueuedEvent.getContext().getException();
// ExceptionUtil.handle(exception, false);
// }
// // rebuild validation exceptions to change the labels
// super.handle();
// }
//
// }
