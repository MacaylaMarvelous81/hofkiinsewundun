package com.jomarm.entry;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.PolicyFactory;

import java.util.Date;
import java.util.List;

@Entity
public class Entry extends PanacheEntity {
    public static final PolicyFactory POLICY_DEFINITION = new HtmlPolicyBuilder()
        .allowStandardUrlProtocols()
        .allowCommonBlockElements()
        .allowCommonInlineFormattingElements()
        .allowStyling()
        .allowAttributes("title").onElements("a", "abbr", "acronym", "img")
        .allowAttributes("href").onElements("a")
        .allowAttributes("src", "alt").onElements("img")
        .allowAttributes("summary").onElements("table")
        .allowAttributes("align", "valign").onElements("table", "tr", "td", "th", "colgroup", "col", "thead", "tbody", "tfoot")
        .allowElements("table", "tr", "td", "th", "colgroup", "caption", "col", "thead", "tbody", "tfoot")
        .requireRelNofollowOnLinks()
        .toFactory();

    public EntryType type;
    public String name;
    public String summary;
    public String htmlContent;
    public Date published;
    public Date updated;
    @ManyToOne
    public Entry replyTo;
    @Column(unique = true)
    public String legacyId;

    public String getSafeHtmlContent() {
        return POLICY_DEFINITION.sanitize(htmlContent);
    }

    public static List<Entry> findByType(EntryType type) {
        return find("type = ?1 ORDER BY id", type).list();
    }

    public static Entry findByLegacyId(String legacyId) {
        return find("legacyId = ?1", legacyId).firstResult();
    }
}
