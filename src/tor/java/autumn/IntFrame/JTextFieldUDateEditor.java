/*
 *  Copyright (C) 2006 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package tor.java.autumn.IntFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.MaskFormatter;

import JCommonTools.CC;
import JCommonTools.Convert;
import JCommonTools.UDate.UDate;

import com.toedter.calendar.DateUtil;
import com.toedter.calendar.IDateEditor;

/**
 * JTextFieldDateEditor is the default editor used by JDateChooser. It is a
 * formatted text field, that colores valid dates green/black and invalid dates
 * red. The date format patten and mask can be set manually. If not set, the
 * MEDIUM pattern of a SimpleDateFormat with regards to the actual locale is
 * used.
 * 
 * @author Kai Toedter
 * @version $LastChangedRevision: 97 $
 * @version $LastChangedDate: 2006-05-24 17:30:41 +0200 (Mi, 24 Mai 2006) $
 */
public class JTextFieldUDateEditor extends JFormattedTextField implements IDateEditor,
		CaretListener, FocusListener, ActionListener {

	private static final long serialVersionUID = -8901842591101625304L;

	protected Date date;
	protected UDate udt;

	protected SimpleDateFormat dateFormatter;

	protected MaskFormatter maskFormatter;

	protected String datePattern;

	protected String maskPattern;

	protected char placeholder;

	protected Color darkGreen;

	protected DateUtil dateUtil;

	private boolean isMaskVisible;

	private boolean ignoreDatePatternChange;

	private int hours;

	private int minutes;

	private int seconds;

	private int millis;

	private Calendar calendar;

	public JTextFieldUDateEditor() 
	{
		this.maskPattern = "**.**.****";
		this.placeholder = '_';
		
		dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
		dateFormatter.setLenient(false);

		setDateFormatString(null);

		//setToolTipText(this.datePattern);
		
		setMaskVisible(true);

		addCaretListener(this);
		addFocusListener(this);
		addActionListener(this);
		darkGreen = new Color(0, 150, 0);

		calendar = Calendar.getInstance();

		udt = new UDate();
		dateUtil = new DateUtil();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getDate()
	 */
	public Date getDate() {
		try {
			calendar.setTime(dateFormatter.parse(getText()));
			calendar.set(Calendar.HOUR_OF_DAY, hours);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, seconds);
			calendar.set(Calendar.MILLISECOND, millis);
			date = calendar.getTime();
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}

	public String getDateYYYMMDD()
	{
		if (getDate() != null)
		{
			udt.setDate(date);
		}
		else
		{
			String st = getText();
			if (st != null && st.length() > 6)
			{
				udt.setDay(Convert.ToIntegerOrZero(st.substring(0, 2)));
				udt.setMonth(Convert.ToIntegerOrZero(st.substring(3, 5)));
				udt.setYear(Convert.ToIntegerOrZero(st.substring(6)));
			}
			else
			{
				udt.Initialize();
			}
		}
		return udt.toString(); 
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setDate(java.util.Date)
	 */
	public void setDate(Date date) {
		setDate(date, true);
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the date
	 * @param firePropertyChange
	 *            true, if the date property should be fired.
	 */
	protected void setDate(Date date, boolean firePropertyChange) {
		Date oldDate = this.date;
		this.date = date;

		if (date == null) {
			setText("");
		} else {
			calendar.setTime(date);
			hours = calendar.get(Calendar.HOUR_OF_DAY);
			minutes = calendar.get(Calendar.MINUTE);
			seconds = calendar.get(Calendar.SECOND);
			millis = calendar.get(Calendar.MILLISECOND);

			String formattedDate = dateFormatter.format(date);
			try {
				setText(formattedDate);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		if (date != null && dateUtil.checkDate(date)) {
			setForeground(Color.BLACK);
		}

		if (firePropertyChange) {
			firePropertyChange("date", oldDate, date);
		}
	}

	public void setDate(String aYYYYMMDD)
	{
		udt.setDateFromStringFormat(aYYYYMMDD);
		
		Date oldDate = this.date;
		this.date = udt.getDate();
		
		try 
		{
			if (this.date != null) 
			{
				calendar.setTime(date);
				hours = calendar.get(Calendar.HOUR_OF_DAY);
				minutes = calendar.get(Calendar.MINUTE);
				seconds = calendar.get(Calendar.SECOND);
				millis = calendar.get(Calendar.MILLISECOND);
			}
			
			String iu = new String(new char[] {placeholder, placeholder});
			String ttt = udt.getDayAsDD(iu) + "."	+ udt.getMonthAsMM(iu) + "."	+udt.getYearAsYYYY(iu+iu);  
			setText(ttt);
			//setText(udt.getDayAsDD(iu) + "."	+ udt.getMonthAsMM(iu) + "."	+udt.getYearAsYYYY(iu+iu));
		} 
		catch (RuntimeException e) 
		{
			e.printStackTrace();
		}

		if (this.date != null && dateUtil.checkDate(this.date)) 
		{
			setForeground(Color.BLACK);
		}

		firePropertyChange("date", oldDate, this.date);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setDateFormatString(java.lang.String)
	 */
 	public void setDateFormatString(String dateFormatString) 
 	{
//		if (ignoreDatePatternChange) {
//			return;
//		}
//
//		try {
//			dateFormatter.applyPattern(dateFormatString);
//		} catch (RuntimeException e) {
//			dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM);
//			dateFormatter.setLenient(false);
//		}
//		this.datePattern = dateFormatter.toPattern();
//		setToolTipText(this.datePattern);
//		setDate(date, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getDateFormatString()
	 */
	public String getDateFormatString() {
		return datePattern;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getUiComponent()
	 */
	public JComponent getUiComponent() {
		return this;
	}

	/**
	 * After any user input, the value of the textfield is proofed. Depending on
	 * being a valid date, the value is colored green or red.
	 * 
	 * @param event
	 *            the caret event
	 */
	public void caretUpdate(CaretEvent event) {
		String text = getText().trim();
		String emptyMask = maskPattern.replace('#', placeholder);

		if (text.length() == 0 || text.equals(emptyMask)) {
			setForeground(Color.BLACK);
			return;
		}

		try {
			Date date = dateFormatter.parse(getText());
			if (dateUtil.checkDate(date)) {
				setForeground(darkGreen);
			} else {
				setForeground(Color.RED);
			}
		} catch (Exception e) {
			setForeground(Color.RED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent focusEvent) {
		checkText();
	}

	private void checkText() {
		try {
			Date date = dateFormatter.parse(getText());
			setDate(date, true);
		} catch (Exception e) {
			// ignore
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale locale) {
		if (locale == getLocale() || ignoreDatePatternChange) {
			return;
		}

		super.setLocale(locale);
		dateFormatter = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		setToolTipText(dateFormatter.toPattern());

		setDate(date, false);
		doLayout();
	}

	/**
	 * Creates a mask from a date pattern. This is a very simple (and
	 * incomplete) implementation thet works only with numbers. A date pattern
	 * of "MM/dd/yy" will result in the mask "##/##/##". Probably you want to
	 * override this method if it does not fit your needs.
	 * 
	 * @param datePattern
	 *            the date pattern
	 * @return the mask
	 */
	public String createMaskFromDatePattern(String datePattern) {
		String symbols = "GyMdkHmsSEDFwWahKzZ";
		String mask = "";
		for (int i = 0; i < datePattern.length(); i++) {
			char ch = datePattern.charAt(i);
			boolean symbolFound = false;
			for (int n = 0; n < symbols.length(); n++) {
				if (symbols.charAt(n) == ch) {
					mask += "#";
					symbolFound = true;
					break;
				}
			}
			if (!symbolFound) {
				mask += ch;
			}
		}
		return mask;
	}

	/**
	 * Returns true, if the mask is visible.
	 * 
	 * @return true, if the mask is visible
	 */
	public boolean isMaskVisible() {
		return isMaskVisible;
	}

	/**
	 * Sets the mask visible.
	 * 
	 * @param isMaskVisible
	 *            true, if the mask should be visible
	 */
	public void setMaskVisible(boolean isMaskVisible) {
		this.isMaskVisible = isMaskVisible;
		if (isMaskVisible) {
			if (maskFormatter == null) {
				try {
					maskFormatter = new MaskFormatter(this.maskPattern);
					//maskFormatter = new MaskFormatter(createMaskFromDatePattern(datePattern));
					maskFormatter.setPlaceholderCharacter(this.placeholder);
					maskFormatter.install(this);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns the preferred size. If a date pattern is set, it is the size the
	 * date pattern would take.
	 */
	public Dimension getPreferredSize() {
		if (datePattern != null) {
			return new JTextField(datePattern).getPreferredSize();
		}
		return super.getPreferredSize();
	}

	/**
	 * Validates the typed date and sets it (only if it is valid).
	 */
	public void actionPerformed(ActionEvent e) {
		checkText();
	}

	/**
	 * Enables and disabled the compoment. It also fixes the background bug
	 * 4991597 and sets the background explicitely to a
	 * TextField.inactiveBackground.
	 */
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		if (!b) {
			super.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getMaxSelectableDate()
	 */
	public Date getMaxSelectableDate() {
		return dateUtil.getMaxSelectableDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#getMinSelectableDate()
	 */
	public Date getMinSelectableDate() {
		return dateUtil.getMinSelectableDate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setMaxSelectableDate(java.util.Date)
	 */
	public void setMaxSelectableDate(Date max) {
		dateUtil.setMaxSelectableDate(max);
		checkText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setMinSelectableDate(java.util.Date)
	 */
	public void setMinSelectableDate(Date min) {
		dateUtil.setMinSelectableDate(min);
		checkText();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.toedter.calendar.IDateEditor#setSelectableDateRange(java.util.Date,
	 *      java.util.Date)
	 */
	public void setSelectableDateRange(Date min, Date max) {
		dateUtil.setSelectableDateRange(min, max);
		checkText();
	}
}
