/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b@\b\u0086\u0001\u0018\u0000 F2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001FB+\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0007\"\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u001b\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0007\u00a2\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000ej\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)j\u0002\b*j\u0002\b+j\u0002\b,j\u0002\b-j\u0002\b.j\u0002\b/j\u0002\b0j\u0002\b1j\u0002\b2j\u0002\b3j\u0002\b4j\u0002\b5j\u0002\b6j\u0002\b7j\u0002\b8j\u0002\b9j\u0002\b:j\u0002\b;j\u0002\b<j\u0002\b=j\u0002\b>j\u0002\b?j\u0002\b@j\u0002\bAj\u0002\bBj\u0002\bCj\u0002\bDj\u0002\bE\u00a8\u0006G"}, d2={"Lio/javalin/http/ContentType;", "", "mimeType", "", "isHumanReadable", "", "extensions", "", "(Ljava/lang/String;ILjava/lang/String;Z[Ljava/lang/String;)V", "getExtensions", "()[Ljava/lang/String;", "[Ljava/lang/String;", "()Z", "getMimeType", "()Ljava/lang/String;", "toString", "TEXT_PLAIN", "TEXT_CSS", "TEXT_CSV", "TEXT_HTML", "TEXT_JS", "TEXT_MARKDOWN", "TEXT_PROPERTIES", "TEXT_XML", "IMAGE_AVIF", "IMAGE_BMP", "IMAGE_GIF", "IMAGE_ICO", "IMAGE_JPEG", "IMAGE_PNG", "IMAGE_SVG", "IMAGE_TIFF", "IMAGE_WEBP", "AUDIO_AAC", "AUDIO_MIDI", "AUDIO_MPEG", "AUDIO_OGA", "AUDIO_OPUS", "AUDIO_WAV", "AUDIO_WEBA", "VIDEO_AVI", "VIDEO_MP4", "VIDEO_MPEG", "VIDEO_OGG", "VIDEO_WEBM", "FONT_OTF", "FONT_TTF", "APPLICATION_OCTET_STREAM", "APPLICATION_BZ", "APPLICATION_BZ2", "APPLICATION_CDN", "APPLICATION_DOC", "APPLICATION_DOCX", "APPLICATION_EPUB", "APPLICATION_GZ", "APPLICATION_JS", "APPLICATION_JSON", "APPLICATION_MPKG", "APPLICATION_JAR", "APPLICATION_PDF", "APPLICATION_POM", "APPLICATION_RAR", "APPLICATION_SH", "APPLICATION_SWF", "APPLICATION_TAR", "APPLICATION_XHTML", "APPLICATION_YAML", "APPLICATION_ZIP", "APPLICATION_7Z", "MULTIPART_FORM_DATA", "Companion", "javalin"})
public final class ContentType
extends Enum<ContentType> {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final String mimeType;
    private final boolean isHumanReadable;
    @NotNull
    private final String[] extensions;
    @NotNull
    public static final String PLAIN = "text/plain";
    @NotNull
    public static final String CSS = "text/css";
    @NotNull
    public static final String HTML = "text/html";
    @NotNull
    public static final String XML = "text/xml";
    @NotNull
    public static final String OCTET_STREAM = "application/octet-stream";
    @NotNull
    public static final String JAVASCRIPT = "application/javascript";
    @NotNull
    public static final String JAVASCRIPT_LEGACY = "application/javascript";
    @NotNull
    public static final String JAVASCRIPT_MODERN = "text/javascript";
    @NotNull
    public static final String JSON = "application/json";
    @NotNull
    public static final String FORM_DATA = "multipart/form-data";
    public static final /* enum */ ContentType TEXT_PLAIN;
    public static final /* enum */ ContentType TEXT_CSS;
    public static final /* enum */ ContentType TEXT_CSV;
    public static final /* enum */ ContentType TEXT_HTML;
    public static final /* enum */ ContentType TEXT_JS;
    public static final /* enum */ ContentType TEXT_MARKDOWN;
    public static final /* enum */ ContentType TEXT_PROPERTIES;
    public static final /* enum */ ContentType TEXT_XML;
    public static final /* enum */ ContentType IMAGE_AVIF;
    public static final /* enum */ ContentType IMAGE_BMP;
    public static final /* enum */ ContentType IMAGE_GIF;
    public static final /* enum */ ContentType IMAGE_ICO;
    public static final /* enum */ ContentType IMAGE_JPEG;
    public static final /* enum */ ContentType IMAGE_PNG;
    public static final /* enum */ ContentType IMAGE_SVG;
    public static final /* enum */ ContentType IMAGE_TIFF;
    public static final /* enum */ ContentType IMAGE_WEBP;
    public static final /* enum */ ContentType AUDIO_AAC;
    public static final /* enum */ ContentType AUDIO_MIDI;
    public static final /* enum */ ContentType AUDIO_MPEG;
    public static final /* enum */ ContentType AUDIO_OGA;
    public static final /* enum */ ContentType AUDIO_OPUS;
    public static final /* enum */ ContentType AUDIO_WAV;
    public static final /* enum */ ContentType AUDIO_WEBA;
    public static final /* enum */ ContentType VIDEO_AVI;
    public static final /* enum */ ContentType VIDEO_MP4;
    public static final /* enum */ ContentType VIDEO_MPEG;
    public static final /* enum */ ContentType VIDEO_OGG;
    public static final /* enum */ ContentType VIDEO_WEBM;
    public static final /* enum */ ContentType FONT_OTF;
    public static final /* enum */ ContentType FONT_TTF;
    public static final /* enum */ ContentType APPLICATION_OCTET_STREAM;
    public static final /* enum */ ContentType APPLICATION_BZ;
    public static final /* enum */ ContentType APPLICATION_BZ2;
    public static final /* enum */ ContentType APPLICATION_CDN;
    public static final /* enum */ ContentType APPLICATION_DOC;
    public static final /* enum */ ContentType APPLICATION_DOCX;
    public static final /* enum */ ContentType APPLICATION_EPUB;
    public static final /* enum */ ContentType APPLICATION_GZ;
    @Deprecated(message="use TEXT_JS instead.", replaceWith=@ReplaceWith(expression="io.javalin.http.ContentType.TEXT_JS", imports={}), level=DeprecationLevel.WARNING)
    public static final /* enum */ ContentType APPLICATION_JS;
    public static final /* enum */ ContentType APPLICATION_JSON;
    public static final /* enum */ ContentType APPLICATION_MPKG;
    public static final /* enum */ ContentType APPLICATION_JAR;
    public static final /* enum */ ContentType APPLICATION_PDF;
    public static final /* enum */ ContentType APPLICATION_POM;
    public static final /* enum */ ContentType APPLICATION_RAR;
    public static final /* enum */ ContentType APPLICATION_SH;
    public static final /* enum */ ContentType APPLICATION_SWF;
    public static final /* enum */ ContentType APPLICATION_TAR;
    public static final /* enum */ ContentType APPLICATION_XHTML;
    public static final /* enum */ ContentType APPLICATION_YAML;
    public static final /* enum */ ContentType APPLICATION_ZIP;
    public static final /* enum */ ContentType APPLICATION_7Z;
    public static final /* enum */ ContentType MULTIPART_FORM_DATA;
    private static final /* synthetic */ ContentType[] $VALUES;

    private ContentType(String mimeType, boolean isHumanReadable, String ... extensions) {
        this.mimeType = mimeType;
        this.isHumanReadable = isHumanReadable;
        this.extensions = extensions;
    }

    @NotNull
    public final String getMimeType() {
        return this.mimeType;
    }

    public final boolean isHumanReadable() {
        return this.isHumanReadable;
    }

    @NotNull
    public final String[] getExtensions() {
        return this.extensions;
    }

    @NotNull
    public String toString() {
        return this.mimeType;
    }

    public static ContentType[] values() {
        return (ContentType[])$VALUES.clone();
    }

    public static ContentType valueOf(String value) {
        return Enum.valueOf(ContentType.class, value);
    }

    @JvmStatic
    @Nullable
    public static final ContentType getContentType(@NotNull String mimeType) {
        return Companion.getContentType(mimeType);
    }

    @JvmStatic
    @Nullable
    public static final ContentType getContentTypeByExtension(@NotNull String extension) {
        return Companion.getContentTypeByExtension(extension);
    }

    @JvmStatic
    @Nullable
    public static final String getMimeTypeByExtension(@NotNull String extensions) {
        return Companion.getMimeTypeByExtension(extensions);
    }

    static {
        String[] stringArray = new String[]{"txt"};
        TEXT_PLAIN = new ContentType(PLAIN, true, stringArray);
        stringArray = new String[]{"css"};
        TEXT_CSS = new ContentType(CSS, true, stringArray);
        stringArray = new String[]{"csv"};
        TEXT_CSV = new ContentType("text/csv", false, stringArray);
        stringArray = new String[]{"html", "htm"};
        TEXT_HTML = new ContentType(HTML, true, stringArray);
        stringArray = new String[]{"js", "mjs"};
        TEXT_JS = new ContentType(JAVASCRIPT_MODERN, true, stringArray);
        stringArray = new String[]{"md"};
        TEXT_MARKDOWN = new ContentType("text/markdown", true, stringArray);
        stringArray = new String[]{"properties"};
        TEXT_PROPERTIES = new ContentType("text/x-java-properties", true, stringArray);
        stringArray = new String[]{"xml"};
        TEXT_XML = new ContentType(XML, true, stringArray);
        stringArray = new String[]{"avif"};
        IMAGE_AVIF = new ContentType("image/avif", true, stringArray);
        stringArray = new String[]{"bmp"};
        IMAGE_BMP = new ContentType("image/bmp", true, stringArray);
        stringArray = new String[]{"gif"};
        IMAGE_GIF = new ContentType("image/gif", true, stringArray);
        stringArray = new String[]{"ico"};
        IMAGE_ICO = new ContentType("image/vnd.microsoft.icon", true, stringArray);
        stringArray = new String[]{"jpeg", "jpg"};
        IMAGE_JPEG = new ContentType("image/jpeg", true, stringArray);
        stringArray = new String[]{"png"};
        IMAGE_PNG = new ContentType("image/png", true, stringArray);
        stringArray = new String[]{"svg"};
        IMAGE_SVG = new ContentType("image/svg+xml", true, stringArray);
        stringArray = new String[]{"tiff", "tif"};
        IMAGE_TIFF = new ContentType("image/tiff", true, stringArray);
        stringArray = new String[]{"webp"};
        IMAGE_WEBP = new ContentType("image/webp", true, stringArray);
        stringArray = new String[]{"aac"};
        AUDIO_AAC = new ContentType("audio/aac", true, stringArray);
        stringArray = new String[]{"mid", "midi"};
        AUDIO_MIDI = new ContentType("audio/midi", true, stringArray);
        stringArray = new String[]{"mp3"};
        AUDIO_MPEG = new ContentType("audio/mpeg", true, stringArray);
        stringArray = new String[]{"oga"};
        AUDIO_OGA = new ContentType("audio/ogg", true, stringArray);
        stringArray = new String[]{"opus"};
        AUDIO_OPUS = new ContentType("audio/opus", true, stringArray);
        stringArray = new String[]{"wav"};
        AUDIO_WAV = new ContentType("audio/wav", true, stringArray);
        stringArray = new String[]{"waba"};
        AUDIO_WEBA = new ContentType("audio/weba", true, stringArray);
        stringArray = new String[]{"avi"};
        VIDEO_AVI = new ContentType("video/x-msvideo", true, stringArray);
        stringArray = new String[]{"mp4"};
        VIDEO_MP4 = new ContentType("video/mp4", true, stringArray);
        stringArray = new String[]{"mpeg"};
        VIDEO_MPEG = new ContentType("video/mpeg", true, stringArray);
        stringArray = new String[]{"ogg"};
        VIDEO_OGG = new ContentType("video/ogg", true, stringArray);
        stringArray = new String[]{"webm"};
        VIDEO_WEBM = new ContentType("video/webm", true, stringArray);
        stringArray = new String[]{"otf"};
        FONT_OTF = new ContentType("font/otf", false, stringArray);
        stringArray = new String[]{"ttf"};
        FONT_TTF = new ContentType("font/ttf", false, stringArray);
        stringArray = new String[]{"bin"};
        APPLICATION_OCTET_STREAM = new ContentType(OCTET_STREAM, false, stringArray);
        stringArray = new String[]{"bz"};
        APPLICATION_BZ = new ContentType("application/x-bzip", false, stringArray);
        stringArray = new String[]{"bz2"};
        APPLICATION_BZ2 = new ContentType("application/x-bzip2", false, stringArray);
        stringArray = new String[]{"cdn"};
        APPLICATION_CDN = new ContentType("application/cdn", true, stringArray);
        stringArray = new String[]{"doc"};
        APPLICATION_DOC = new ContentType("application/msword", false, stringArray);
        stringArray = new String[]{"docx"};
        APPLICATION_DOCX = new ContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document", false, stringArray);
        stringArray = new String[]{"epub"};
        APPLICATION_EPUB = new ContentType("application/epub+zip", false, stringArray);
        stringArray = new String[]{"gz"};
        APPLICATION_GZ = new ContentType("application/gzip", false, stringArray);
        stringArray = new String[]{"application/javascript"};
        APPLICATION_JS = new ContentType("application/javascript", true, stringArray);
        stringArray = new String[]{"json"};
        APPLICATION_JSON = new ContentType(JSON, true, stringArray);
        stringArray = new String[]{"mpkg"};
        APPLICATION_MPKG = new ContentType("application/vnd.apple.installer+xml", false, stringArray);
        stringArray = new String[]{"jar"};
        APPLICATION_JAR = new ContentType("application/java-archive", false, stringArray);
        stringArray = new String[]{"pdf"};
        APPLICATION_PDF = new ContentType("application/pdf", true, stringArray);
        stringArray = new String[]{"pom"};
        APPLICATION_POM = new ContentType("application/xml", true, stringArray);
        stringArray = new String[]{"rar"};
        APPLICATION_RAR = new ContentType("application/vnd.rar", false, stringArray);
        stringArray = new String[]{"sh"};
        APPLICATION_SH = new ContentType("application/x-sh", true, stringArray);
        stringArray = new String[]{"swf"};
        APPLICATION_SWF = new ContentType("application/x-shockwave-flash", false, stringArray);
        stringArray = new String[]{"tar"};
        APPLICATION_TAR = new ContentType("application/x-tar", false, stringArray);
        stringArray = new String[]{"xhtml"};
        APPLICATION_XHTML = new ContentType("application/xhtml+xml", true, stringArray);
        stringArray = new String[]{"yaml", "yml"};
        APPLICATION_YAML = new ContentType("application/yaml", true, stringArray);
        stringArray = new String[]{"zip"};
        APPLICATION_ZIP = new ContentType("application/zip", false, stringArray);
        stringArray = new String[]{"7z"};
        APPLICATION_7Z = new ContentType("application/x-7z-compressed", false, stringArray);
        stringArray = new String[]{FORM_DATA};
        MULTIPART_FORM_DATA = new ContentType(FORM_DATA, false, stringArray);
        $VALUES = contentTypeArray = new ContentType[]{ContentType.TEXT_PLAIN, ContentType.TEXT_CSS, ContentType.TEXT_CSV, ContentType.TEXT_HTML, ContentType.TEXT_JS, ContentType.TEXT_MARKDOWN, ContentType.TEXT_PROPERTIES, ContentType.TEXT_XML, ContentType.IMAGE_AVIF, ContentType.IMAGE_BMP, ContentType.IMAGE_GIF, ContentType.IMAGE_ICO, ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG, ContentType.IMAGE_SVG, ContentType.IMAGE_TIFF, ContentType.IMAGE_WEBP, ContentType.AUDIO_AAC, ContentType.AUDIO_MIDI, ContentType.AUDIO_MPEG, ContentType.AUDIO_OGA, ContentType.AUDIO_OPUS, ContentType.AUDIO_WAV, ContentType.AUDIO_WEBA, ContentType.VIDEO_AVI, ContentType.VIDEO_MP4, ContentType.VIDEO_MPEG, ContentType.VIDEO_OGG, ContentType.VIDEO_WEBM, ContentType.FONT_OTF, ContentType.FONT_TTF, ContentType.APPLICATION_OCTET_STREAM, ContentType.APPLICATION_BZ, ContentType.APPLICATION_BZ2, ContentType.APPLICATION_CDN, ContentType.APPLICATION_DOC, ContentType.APPLICATION_DOCX, ContentType.APPLICATION_EPUB, ContentType.APPLICATION_GZ, ContentType.APPLICATION_JS, ContentType.APPLICATION_JSON, ContentType.APPLICATION_MPKG, ContentType.APPLICATION_JAR, ContentType.APPLICATION_PDF, ContentType.APPLICATION_POM, ContentType.APPLICATION_RAR, ContentType.APPLICATION_SH, ContentType.APPLICATION_SWF, ContentType.APPLICATION_TAR, ContentType.APPLICATION_XHTML, ContentType.APPLICATION_YAML, ContentType.APPLICATION_ZIP, ContentType.APPLICATION_7Z, ContentType.MULTIPART_FORM_DATA};
        Companion = new Companion(null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0004H\u0007J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0013\u001a\u00020\u0004H\u0007J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0015\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u00020\u00048\u0006X\u0087T\u00a2\u0006\b\n\u0000\u0012\u0004\b\b\u0010\u0002R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2={"Lio/javalin/http/ContentType$Companion;", "", "()V", "CSS", "", "FORM_DATA", "HTML", "JAVASCRIPT", "getJAVASCRIPT$annotations", "JAVASCRIPT_LEGACY", "JAVASCRIPT_MODERN", "JSON", "OCTET_STREAM", "PLAIN", "XML", "getContentType", "Lio/javalin/http/ContentType;", "mimeType", "getContentTypeByExtension", "extension", "getMimeTypeByExtension", "extensions", "javalin"})
    @SourceDebugExtension(value={"SMAP\nContentType.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContentType.kt\nio/javalin/http/ContentType$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,146:1\n1#2:147\n1282#3:148\n12744#3,2:149\n1283#3:151\n*S KotlinDebug\n*F\n+ 1 ContentType.kt\nio/javalin/http/ContentType$Companion\n*L\n133#1:148\n134#1:149,2\n133#1:151\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @Deprecated(message="represents the older application/javascript mimetype. This will change to represent text/javascript instead. Please use either JAVASCRIPT_LEGACY or JAVASCRIPT_MODERN instead to be explicit", replaceWith=@ReplaceWith(expression="io.javalin.http.ContentType.JAVASCRIPT_MODERN", imports={}), level=DeprecationLevel.WARNING)
        public static /* synthetic */ void getJAVASCRIPT$annotations() {
        }

        @JvmStatic
        @Nullable
        public final ContentType getContentType(@NotNull String mimeType) {
            ContentType contentType;
            block1: {
                Intrinsics.checkNotNullParameter(mimeType, "mimeType");
                ContentType[] contentTypeArray = ContentType.values();
                int n = contentTypeArray.length;
                for (int i = 0; i < n; ++i) {
                    ContentType contentType2;
                    ContentType it = contentType2 = contentTypeArray[i];
                    boolean bl = false;
                    if (!StringsKt.equals(it.getMimeType(), mimeType, true)) continue;
                    contentType = contentType2;
                    break block1;
                }
                contentType = null;
            }
            return contentType;
        }

        @JvmStatic
        @Nullable
        public final ContentType getContentTypeByExtension(@NotNull String extension) {
            ContentType contentType;
            block3: {
                Intrinsics.checkNotNullParameter(extension, "extension");
                ContentType[] $this$firstOrNull$iv = ContentType.values();
                boolean $i$f$firstOrNull = false;
                int n = $this$firstOrNull$iv.length;
                for (int i = 0; i < n; ++i) {
                    boolean bl;
                    ContentType element$iv;
                    block2: {
                        ContentType type = element$iv = $this$firstOrNull$iv[i];
                        boolean bl2 = false;
                        String[] $this$any$iv = type.getExtensions();
                        boolean $i$f$any = false;
                        int n2 = $this$any$iv.length;
                        for (int j = 0; j < n2; ++j) {
                            String element$iv2;
                            String it = element$iv2 = $this$any$iv[j];
                            boolean bl3 = false;
                            if (!StringsKt.equals(extension, it, true)) continue;
                            bl = true;
                            break block2;
                        }
                        bl = false;
                    }
                    if (!bl) continue;
                    contentType = element$iv;
                    break block3;
                }
                contentType = null;
            }
            return contentType;
        }

        @JvmStatic
        @Nullable
        public final String getMimeTypeByExtension(@NotNull String extensions) {
            Intrinsics.checkNotNullParameter(extensions, "extensions");
            ContentType contentType = this.getContentTypeByExtension(extensions);
            return contentType != null ? contentType.getMimeType() : null;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

