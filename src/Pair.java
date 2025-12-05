/*
 *
 *  ---------------------------------------------------------------------------------------------------------
 *              Titel: Pair.java
 *             Auteur: ruijj03
 *    Creatietijdstip: 05/12/2025 14:00
 *          Copyright: (c) 2025 Belastingdienst / Centrum voor Applicatieontwikkeling en Onderhoud,
 *                     All Rights Reserved.
 *  ---------------------------------------------------------------------------------------------------------
 *                                              |   Unpublished work. This computer program includes
 *     De Belastingdienst                       |   Confidential, Properietary Information and is a
 *     Postbus 9050                             |   trade Secret of the Belastingdienst. No part of
 *     7300 GM  Apeldoorn                       |   this file may be reproduced or transmitted in any
 *     The Netherlands                          |   form or by any means, electronic or mechanical,
 *     http://belastingdienst.nl/               |   for the purpose, without the express written
 *                                              |   permission of the copyright holder.
 *  ---------------------------------------------------------------------------------------------------------
 *
 */

/**
 * TODO: ruijj03: beschrijf deze klasse !
 *
 * @author ruijj03
 */
public class Pair {
	private long x;
	private long y;

	public Pair(long x, long y) {
		this.x = x;
		this.y = y;
	}

	public boolean isBetweenInclusive(long value) {
		return x <= value && value <= y;
	}

	public Long numberOfProducts() {
		return y - x ;
	}

	public boolean includes(Pair p) {
		if (p == this) { return false;}
		System.out.println(this + " " + p + " => " + (x >= p.x && y <= p.y));
		return x >= p.x && y <= p.y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

	public boolean overlaps(Pair other) {
		if (this == other) return false;

		return x < other.y && y > other.x;
	}

	public void merge(Pair pair) {
		if (pair.x < x) {
			x = pair.x;
		}
		if (pair.y > y) {
			y = pair.y;
		}
	}
}
