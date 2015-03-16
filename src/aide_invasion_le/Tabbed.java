package aide_invasion_le;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * La classe Tabbed permet de modifier l'apparence des onglets du JtabbedPane et
 * elle extends la class BasicTabbedPaneUI.
 */
public class Tabbed extends BasicTabbedPaneUI {
	private FontMetrics boldFontMetrics;
	private Font boldFont;

	protected void paintTabBackground(Graphics g, int tabPlacement,
			int tabIndex, int x, int y, int w, int h, boolean isSelected) {
		g.setColor(new Color(170, 180, 179));
		g.fillRect(x, y, w, h);
		if (isSelected) {
			g.setColor(new Color(62, 193, 189));
			g.fillRect(x, y, w, h);
		}
	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
			int x, int y, int w, int h, boolean isSelected) {
		g.setColor(new Color(19, 19, 19));
		g.drawRect(x, y, w, h);
		if (isSelected)
			g.setColor(new Color(121, 134, 133));
	}

	/**
	 * Permet de redéfinir le focus, quand on a le focus sur un onglet un petit
	 * rectangle en pointillé apparaît en redéfinissant la méthode on peut
	 * supprimer ce comportement.
	 */
	protected void paintFocusIndicator(Graphics g, int tabPlacement,
			Rectangle[] rects, int tabIndex, Rectangle iconRect,
			Rectangle textRect, boolean isSelected) {
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex,
			int fontHeight) {
		int vHeight = fontHeight;
		if (vHeight % 2 > 0)
			vHeight += 2;
		return vHeight;
	}

	protected int calculateTabWidth(int tabPlacement, int tabIndex,
			FontMetrics metrics) {
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics)
				+ metrics.getHeight() + 15;
	}
	
	protected void installDefaults() {
		super.installDefaults();
		tabAreaInsets.left = 0;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);
		tabInsets = selectedTabPadInsets;
		boldFont = tabPane.getFont().deriveFont(Font.BOLD);
		boldFontMetrics = tabPane.getFontMetrics(boldFont);
	}

	protected void paintText(Graphics g, int tabPlacement, Font font,
			FontMetrics metrics, int tabIndex, String title,
			Rectangle textRect, boolean isSelected) {
		if (isSelected) {
			int vDifference = (int) (boldFontMetrics.getStringBounds(title, g)
					.getWidth()) - textRect.width;
			textRect.x -= (vDifference / 2);
			super.paintText(g, tabPlacement, boldFont, boldFontMetrics,
					tabIndex, title, textRect, isSelected);
		} else
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title,
					textRect, isSelected);
	}
}