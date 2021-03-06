USE [master]
GO
/****** Object:  Database [HotelCrawlerDB]    Script Date: 10/16/2013 08:36:40 ******/
CREATE DATABASE [HotelCrawlerDB]

GO
USE [HotelCrawlerDB]
GO
/****** Object:  Table [dbo].[Properties]    Script Date: 10/16/2013 08:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Properties](
	[PropertyID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](50) NULL,
	[Description] [nvarchar](3000) NULL,
 CONSTRAINT [PK_Properties] PRIMARY KEY CLUSTERED 
(
	[PropertyID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Website]    Script Date: 10/16/2013 08:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Website](
	[WebsiteID] [int] IDENTITY(1,1) NOT NULL,
	[WebsiteName] [nvarchar](50) NULL,
	[Homepage] [nvarchar](50) NULL,
	[CrawlerSeed] [nvarchar](255) NULL,
	[CrawlerMatch] [nvarchar](255) NULL,
	[CrawlerIDLink] [nvarchar](255) NULL,
	[CrawlerIDFrom] [int] NULL CONSTRAINT [DF_Website_CrawlerIDFrom]  DEFAULT ((1)),
	[CrawlerIDTo] [int] NULL CONSTRAINT [DF_Website_CrawlerIDTo]  DEFAULT ((1000)),
	[CrawlerCheckContent] [nvarchar](255) NULL,
	[CheckInParaName] [nvarchar](50) NULL,
	[CheckOutParaName] [nvarchar](50) NULL,
	[DateFormat] [nvarchar](50) NULL,
	[OtherParaNames] [nvarchar](255) NULL,
	[RequestMethod] [nvarchar](50) NULL CONSTRAINT [DF_Website_RequestMethod]  DEFAULT (N'GET'),
	[UseCookie] [int] NULL CONSTRAINT [DF_Website_UseCookie]  DEFAULT ((0)),
 CONSTRAINT [PK_Website] PRIMARY KEY CLUSTERED 
(
	[WebsiteID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tên của trang nguồn.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'WebsiteName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Địa chỉ trang chủ.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'Homepage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Địa chỉ bắt đầu quét của Crawler' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CrawlerSeed'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Chứa biểu thức Regular Expression. Crawler sẽ xem những URL thỏa mãn biểu thức này là Link Detail (trang thông tin của 1 khách sạn). Nếu giá trị này bằng rỗng thì sử dụng CrawlerIDLink (dùng vòng for).' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CrawlerMatch'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Nhập {id} để thay cho giá trị ID. Trường hợp website có thể tìm được quy luật của Link Detail bằng cách đổi ID. Nếu giá trị này bằng rỗng thì sử dụng CrawlerMatch.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CrawlerIDLink'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Giá trị bắt đầu của ID nếu dùng CrawlerIDLink. Mặc định bắt đầu từ 1.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CrawlerIDFrom'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Giá trị kết thúc của ID nếu dùng CrawlerIDLink. Mặc định là 1000 (tương ứng với 1000 Link Detail)' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CrawlerIDTo'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Chứa một chuỗi ký tự mà chỉ tồn tại trong Link Detail. Dùng để xác định được link đang xét có phải là Link Detail hay không. Dùng trong trường hợp cả CrawlerMatch và CrawlerIDLink đều rỗng.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CrawlerCheckContent'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tên biến (tham số) của giá trị ngày đến (Check In Date) trong request.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CheckInParaName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tên biến (tham số) của giá trị ngày đi (Check Out Date) trong request.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'CheckOutParaName'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Định dạng ngày tháng cho tham số, ví dụ: dd/mm/yyyy' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'DateFormat'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Các biến (tham số) khác cần phải dùng kèm theo request. Viết cặp giá trị ngăn cách nhau bởi dấu &, không có dấu khoảng trắng. Ví dụ: show_price=1&check_availabitity=true&abc=def' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'OtherParaNames'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Phương thức gửi. Là "GET" hoặc "POST". Mặc định là "GET".' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'RequestMethod'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Bằng 1 nếu dùng Cookie, bằng 0 nếu không cần dùng. Mặc định là 0.' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'Website', @level2type=N'COLUMN',@level2name=N'UseCookie'
GO
/****** Object:  Table [dbo].[Room_Properties]    Script Date: 10/16/2013 08:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Room_Properties](
	[WebsiteID] [int] NOT NULL,
	[PropertyID] [int] NOT NULL,
	[Selector] [nvarchar](50) NULL,
	[ResultMode] [nvarchar](50) NULL,
	[AfterRegex] [nvarchar](50) NULL,
 CONSTRAINT [PK_Room_Properties] PRIMARY KEY CLUSTERED 
(
	[WebsiteID] ASC,
	[PropertyID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hotel_Properties]    Script Date: 10/16/2013 08:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hotel_Properties](
	[WebsiteID] [int] NOT NULL,
	[PropertyID] [int] NOT NULL,
	[Selector] [nvarchar](250) NULL,
	[ResultMode] [nvarchar](50) NULL,
	[AfterRegex] [nvarchar](250) NULL,
 CONSTRAINT [PK_Hotel_Properties] PRIMARY KEY CLUSTERED 
(
	[WebsiteID] ASC,
	[PropertyID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[LinkDetail]    Script Date: 10/16/2013 08:36:40 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[LinkDetail](
	[LinkDetailID] [int] IDENTITY(1,1) NOT NULL,
	[WebsiteID] [int] NULL,
	[Url] [nvarchar](255) NULL,
 CONSTRAINT [PK_LinkDetail] PRIMARY KEY CLUSTERED 
(
	[LinkDetailID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  ForeignKey [FK_Hotel_Properties_Properties]    Script Date: 10/16/2013 08:36:40 ******/
ALTER TABLE [dbo].[Hotel_Properties]  WITH CHECK ADD  CONSTRAINT [FK_Hotel_Properties_Properties] FOREIGN KEY([PropertyID])
REFERENCES [dbo].[Properties] ([PropertyID])
GO
ALTER TABLE [dbo].[Hotel_Properties] CHECK CONSTRAINT [FK_Hotel_Properties_Properties]
GO
/****** Object:  ForeignKey [FK_Hotel_Properties_Website]    Script Date: 10/16/2013 08:36:40 ******/
ALTER TABLE [dbo].[Hotel_Properties]  WITH CHECK ADD  CONSTRAINT [FK_Hotel_Properties_Website] FOREIGN KEY([WebsiteID])
REFERENCES [dbo].[Website] ([WebsiteID])
GO
ALTER TABLE [dbo].[Hotel_Properties] CHECK CONSTRAINT [FK_Hotel_Properties_Website]
GO
/****** Object:  ForeignKey [FK_LinkDetail_Website]    Script Date: 10/16/2013 08:36:40 ******/
ALTER TABLE [dbo].[LinkDetail]  WITH CHECK ADD  CONSTRAINT [FK_LinkDetail_Website] FOREIGN KEY([WebsiteID])
REFERENCES [dbo].[Website] ([WebsiteID])
GO
ALTER TABLE [dbo].[LinkDetail] CHECK CONSTRAINT [FK_LinkDetail_Website]
GO
/****** Object:  ForeignKey [FK_Room_Properties_Properties]    Script Date: 10/16/2013 08:36:40 ******/
ALTER TABLE [dbo].[Room_Properties]  WITH CHECK ADD  CONSTRAINT [FK_Room_Properties_Properties] FOREIGN KEY([PropertyID])
REFERENCES [dbo].[Properties] ([PropertyID])
GO
ALTER TABLE [dbo].[Room_Properties] CHECK CONSTRAINT [FK_Room_Properties_Properties]
GO
/****** Object:  ForeignKey [FK_Room_Properties_Website]    Script Date: 10/16/2013 08:36:40 ******/
ALTER TABLE [dbo].[Room_Properties]  WITH CHECK ADD  CONSTRAINT [FK_Room_Properties_Website] FOREIGN KEY([WebsiteID])
REFERENCES [dbo].[Website] ([WebsiteID])
GO
ALTER TABLE [dbo].[Room_Properties] CHECK CONSTRAINT [FK_Room_Properties_Website]
GO
USE [HotelCrawlerDB];
SET NOCOUNT ON;
SET XACT_ABORT ON;
GO

SET IDENTITY_INSERT [dbo].[Properties] ON;

BEGIN TRANSACTION;
INSERT INTO [dbo].[Properties]([PropertyID], [Name], [Description])
SELECT 1, N'HotelName', N'Tên khách sạn' UNION ALL
SELECT 2, N'Address', N'Địa chỉ khách sạn' UNION ALL
SELECT 3, N'Region', N'Tỉnh thành / khu vực của khách sạn' UNION ALL
SELECT 4, N'Description', N'Mô tả ngắn về khách sạn' UNION ALL
SELECT 5, N'Stars', N'Số sao của khách sạn' UNION ALL
SELECT 6, N'Images', N'Hình ảnh của khách sạn' UNION ALL
SELECT 7, N'Rating', N'Điểm đánh giá của khách sạn' UNION ALL
SELECT 8, N'RoomBlock', N'Đánh dấu block Room (loại phòng) của khách sạn (một khách sạn có nhiều phòng)' UNION ALL
SELECT 9, N'RoomName', N'Tên của loại phòng' UNION ALL
SELECT 10, N'RoomMax', N'Số người tối đa' UNION ALL
SELECT 11, N'RoomPrice', N'Giá phòng' UNION ALL
SELECT 12, N'RoomImages', N'Hình ảnh của phòng' UNION ALL
SELECT 13, N'RoomDescription', N'Mô tả phòng'
COMMIT;
RAISERROR (N'[dbo].[Properties]: Insert Batch: 1.....Done!', 10, 1) WITH NOWAIT;
GO

SET IDENTITY_INSERT [dbo].[Properties] OFF;

SET IDENTITY_INSERT [dbo].[Website] ON;

BEGIN TRANSACTION;
INSERT INTO [dbo].[Website]([WebsiteID], [WebsiteName], [Homepage], [CrawlerSeed], [CrawlerMatch], [CrawlerIDLink], [CrawlerIDFrom], [CrawlerIDTo], [CrawlerCheckContent], [CheckInParaName], [CheckOutParaName], [DateFormat], [OtherParaNames], [RequestMethod], [UseCookie])
SELECT 1, N'YesGo', N'http://yesgo.vn', N'http://www.yesgo.vn/', N'http:\/\/www\.yesgo\.vn\/vi\/khach-san\/viet-nam\/.*?\/.*?\-\d+?.html', N'http://www.yesgo.vn/vi/a/b/c/d-{id}.html', 1, 9000, N'Chọn ngày để có giá chính xác', N'in_date', N'out_date', N'dd/MM/yyyy', N'show_price=1', N'GET', 0 UNION ALL
SELECT 3, N'MangDatPhong', N'http://mangdatphong.vn', N'http://www.mangdatphong.vn/', N'http:\/\/www\.mangdatphong\.vn\/.*?\/.*?\.html', N'http:\/\/www\.mangdatphong\.vn\/.*?\/.*?\.html', 1, 1000, N'Kiểm tra giá và đặt phòng', N'check_in_date', N'check_out_date', N'dd/MM/yyyy', N'check_availability=1', N'POST', 1
COMMIT;
RAISERROR (N'[dbo].[Website]: Insert Batch: 1.....Done!', 10, 1) WITH NOWAIT;
GO

SET IDENTITY_INSERT [dbo].[Website] OFF;

SET IDENTITY_INSERT [dbo].[LinkDetail] ON;

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 29, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/khach-san-havana-nha-trang-9515.html?source=homepage-favorite' UNION ALL
SELECT 30, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/white-sand-doclet-resort-spa-9503.html?source=hoteldetail-relative' UNION ALL
SELECT 31, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-nang/sandy-beach-resort-da-nang-9257.html?source=homepage-favorite' UNION ALL
SELECT 32, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/biet-thu-an-binh-9529.html?source=hoteldetail-relative' UNION ALL
SELECT 417, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/white-sand-doclet-resort-spa-9503.html' UNION ALL
SELECT 418, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-bao-vung-tau-9276.html' UNION ALL
SELECT 419, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sai-gon-85-9510.html' UNION ALL
SELECT 420, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/some-days-of-silence-resort-spa-9779.html' UNION ALL
SELECT 421, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/merperle-resort-residences--9488.html' UNION ALL
SELECT 422, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/biet-thu-an-binh-9529.html' UNION ALL
SELECT 423, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-du-parc-da-lat-7781.html' UNION ALL
SELECT 424, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/blue-moon-hotel-spa-9236.html' UNION ALL
SELECT 425, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-loc-9646.html' UNION ALL
SELECT 426, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-phan-vien-ngan-hang-9666.html' UNION ALL
SELECT 427, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/hue/khach-san-imperial-hue-8181.html' UNION ALL
SELECT 428, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/romana-resort-mui-ne-8341.html' UNION ALL
SELECT 429, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-cao-bang-9577.html' UNION ALL
SELECT 430, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/sunrise-nha-trang-beach-hotel-spa-8225.html' UNION ALL
SELECT 431, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-valley-mountain-8911.html' UNION ALL
SELECT 432, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-doi-dua-9512.html' UNION ALL
SELECT 433, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-bao-thy-2-9642.html' UNION ALL
SELECT 434, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thang-muoi-9507.html' UNION ALL
SELECT 435, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/yen-hideaway-resort-ngoc-suong-resort--9492.html' UNION ALL
SELECT 436, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/merperle-resort-residences-best-western-premier-cu--9488.html' UNION ALL
SELECT 437, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/hoi-an/the-nam-hai-9349.html' UNION ALL
SELECT 438, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-y-linh-9377.html' UNION ALL
SELECT 439, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-plaza-8862.html' UNION ALL
SELECT 440, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-nang/sandy-beach-resort-da-nang-9257.html' UNION ALL
SELECT 441, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-du-parc-da-lat-mercure-du-parc-da-lat-truoc-day-7781.html' UNION ALL
SELECT 442, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/-khu-nghi-duong-an-lam-vinh-ninh-van-bay-9405.html' UNION ALL
SELECT 443, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/khach-san-havana-nha-trang-9515.html' UNION ALL
SELECT 444, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/vinh-suong-seaside-resort-9547.html' UNION ALL
SELECT 445, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-corvin-9500.html' UNION ALL
SELECT 446, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thuy-duong-9647.html' UNION ALL
SELECT 447, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-sai-gon-da-lat-9238.html' UNION ALL
SELECT 448, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sai-gon-quy-nhon-8943.html' UNION ALL
SELECT 449, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-duong-dong-vung-tau-9336.html' UNION ALL
SELECT 450, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/dynasty-beach-resort-8331.html' UNION ALL
SELECT 451, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-la-sapinette-da-lat--9329.html' UNION ALL
SELECT 452, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/non-nuoc-resort-phan-thiet-8318.html' UNION ALL
SELECT 453, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/binh-minh-cable-car-resort-9813.html' UNION ALL
SELECT 454, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/xuyen-moc/sai-gon-binh-chau-resort-9202.html' UNION ALL
SELECT 455, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-the-coast-vung-tau-9295.html' UNION ALL
SELECT 456, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/xuyen-moc/huong-phong-ho-coc-resort-9212.html' UNION ALL
SELECT 457, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/rum-vang-ii-hotel-9252.html' UNION ALL
SELECT 458, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/-khach-san-tuan-chau-morning-star-9562.html' UNION ALL
SELECT 459, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-golf-3-da-lat-9241.html' UNION ALL
SELECT 460, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-vietsovpetro--7785.html' UNION ALL
SELECT 461, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/osaka-resort-da-lat-9410.html' UNION ALL
SELECT 462, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-best-western-da-lat-plaza-9244.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 1.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 463, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-cali-quy-nhon-9370.html' UNION ALL
SELECT 464, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-sammy-9242.html' UNION ALL
SELECT 465, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sand-garden-resort-9548.html' UNION ALL
SELECT 466, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sammy-vung-tau-9427.html' UNION ALL
SELECT 467, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sunflowers-quy-nhon-9131.html' UNION ALL
SELECT 468, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-asean-ha-long-8856.html' UNION ALL
SELECT 469, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/novela-resort-spa-8336.html' UNION ALL
SELECT 470, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-muong-thanh-quy-nhon-9374.html' UNION ALL
SELECT 471, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-sai-gon-ha-long-9112.html' UNION ALL
SELECT 472, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sao-mai-boutique-villas-9763.html' UNION ALL
SELECT 473, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-hai-au-quy-nhon-9373.html' UNION ALL
SELECT 474, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/majestic-nha-trang--9486.html' UNION ALL
SELECT 475, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-dc-9591.html' UNION ALL
SELECT 476, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-thi-thao-gardenia-da-lat--7776.html' UNION ALL
SELECT 477, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-palace--8866.html' UNION ALL
SELECT 478, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/amiana-resort-nha-trang-9560.html' UNION ALL
SELECT 479, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thang-loi-victory--8905.html' UNION ALL
SELECT 480, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/son-thuy-resort-9651.html' UNION ALL
SELECT 481, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-empress-da-lat-7779.html' UNION ALL
SELECT 482, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-river-prince-da-lat-7782.html' UNION ALL
SELECT 483, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-pho-nui-9587.html' UNION ALL
SELECT 484, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-quy-nhon-9470.html' UNION ALL
SELECT 485, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-park-diamond-phan-thiet--8328.html' UNION ALL
SELECT 486, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/da-lat-cadasa-resort-9245.html' UNION ALL
SELECT 487, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ocean-star-khach-san-sao-dai-duong--9282.html' UNION ALL
SELECT 488, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-du-lich-dien-luc-9502.html' UNION ALL
SELECT 489, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-the-palms-9597.html' UNION ALL
SELECT 490, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-kieu-anh-8899.html' UNION ALL
SELECT 491, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sao-mai-resort--8314.html' UNION ALL
SELECT 492, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-romeliess-9182.html' UNION ALL
SELECT 493, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/green-hill-mui-ne-resort-spa--9243.html' UNION ALL
SELECT 494, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-cong-doan-vung-tau-9395.html' UNION ALL
SELECT 495, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/villa-ngoc-my-9310.html' UNION ALL
SELECT 496, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-thao-ha-mui-ne-8316.html' UNION ALL
SELECT 497, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-grand-ha-long-9696.html' UNION ALL
SELECT 498, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-seika-9216.html' UNION ALL
SELECT 499, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/tien-phat-beach-resort-phan-thiet-8320.html' UNION ALL
SELECT 500, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thanh-thuy-9607.html' UNION ALL
SELECT 501, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-dream-9382.html' UNION ALL
SELECT 502, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/golden-sail-hotel-restaurant-9161.html' UNION ALL
SELECT 503, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ocean-side-9213.html' UNION ALL
SELECT 504, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-ocean-front-tien-duong--9181.html' UNION ALL
SELECT 505, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-mai-vang-9305.html' UNION ALL
SELECT 506, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/-royal-lotus-ha-long-8858.html' UNION ALL
SELECT 507, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sandhills-beach-resort-spa-8334.html' UNION ALL
SELECT 508, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-buu-dien-vung-tau-8908.html' UNION ALL
SELECT 509, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/malibu-resort-9491.html' UNION ALL
SELECT 510, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/green-coconut-resort-phan-thiet-9324.html' UNION ALL
SELECT 511, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-eden-quy-nhon-9372.html' UNION ALL
SELECT 512, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/hoang-anh-dat-xanh-dalat-resort-9251.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 2.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 513, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/apricot-resort-bau-mai-resort--9089.html' UNION ALL
SELECT 514, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-green-vung-tau--9122.html' UNION ALL
SELECT 515, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-saphir-da-lat-9195.html' UNION ALL
SELECT 516, 3, N'http://www.mangdatphong.vn/thanh-xuan/khach-san-perfect-ha-noi.html' UNION ALL
SELECT 517, 3, N'http://www.mangdatphong.vn/thanh-xuan/khach-san-hoa-lam-ha-noi.html' UNION ALL
SELECT 518, 3, N'http://www.mangdatphong.vn/da-nang/khach-san-phuong-thu-da-nang.html' UNION ALL
SELECT 519, 3, N'http://www.mangdatphong.vn/thanh-xuan/khach-san-239-ha-noi.html' UNION ALL
SELECT 520, 3, N'http://www.mangdatphong.vn/dien-bien/him-lam-resort-dien-bien.html' UNION ALL
SELECT 521, 3, N'http://www.mangdatphong.vn/da-nang/the-ocean-villas-da-nang.html' UNION ALL
SELECT 522, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-luminous-viet-ha-noi.html' UNION ALL
SELECT 523, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-sai-gon-tourane-da-nang.html' UNION ALL
SELECT 524, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-vinh-trung-plaza-da-nang.html' UNION ALL
SELECT 525, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-twins-ha-noi.html' UNION ALL
SELECT 526, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-golden-silk-ha-noi.html' UNION ALL
SELECT 527, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-nu-hoang-da-nang.html' UNION ALL
SELECT 528, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-eden-plaza-da-nang.html' UNION ALL
SELECT 529, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-tien-thinh-da-nang.html' UNION ALL
SELECT 530, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-paramount.html' UNION ALL
SELECT 531, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-eternity.html' UNION ALL
SELECT 532, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-bao-ngan-ha-noi.html' UNION ALL
SELECT 533, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-song-thu-da-nang.html' UNION ALL
SELECT 534, 3, N'http://www.mangdatphong.vn/ngu-hanh-son/khach-san-holiday-beach-da-nang.html' UNION ALL
SELECT 535, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-dong-thanh-ha-noi.html' UNION ALL
SELECT 536, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-gia-bao-ha-noi.html' UNION ALL
SELECT 537, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-quoc-cuong-ii-da-nang.html' UNION ALL
SELECT 538, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-muong-thanh-da-nang.html' UNION ALL
SELECT 539, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-aquarius-legend-ha-noi.html' UNION ALL
SELECT 540, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-phuong-dong-da-nang.html' UNION ALL
SELECT 541, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-medallion-ha-noi.html' UNION ALL
SELECT 542, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-indochina-queen-2.html' UNION ALL
SELECT 543, 3, N'http://www.mangdatphong.vn/da-nang/khach-san-gold-coast-da-nang.html' UNION ALL
SELECT 544, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ha-noi-happy.html' UNION ALL
SELECT 545, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-flower-garden-ha-noi.html' UNION ALL
SELECT 546, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-tran-ha-noi.html' UNION ALL
SELECT 547, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-cherie-da-nang.html' UNION ALL
SELECT 548, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ha-noi-hibiscus.html' UNION ALL
SELECT 549, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-giany-da-nang.html' UNION ALL
SELECT 550, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ho-guom-ha-noi.html' UNION ALL
SELECT 551, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-lake-side-ha-noi.html' UNION ALL
SELECT 552, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-aphrodite-ha-long.html' UNION ALL
SELECT 553, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-splendid-jupiter.html' UNION ALL
SELECT 554, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-tre-xanh-da-nang.html' UNION ALL
SELECT 555, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-paloma-ha-long.html' UNION ALL
SELECT 556, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-zen-ha-noi.html' UNION ALL
SELECT 557, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-poseidon-ha-long.html' UNION ALL
SELECT 558, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-aphrodite-ha-long.html' UNION ALL
SELECT 559, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-royal-palace-ha-noi.html' UNION ALL
SELECT 560, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-oriental-sails.html' UNION ALL
SELECT 561, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-skylark-ha-noi.html' UNION ALL
SELECT 562, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-luxury-calypso-ha-long.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 3.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 563, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-paloma-ha-long.html' UNION ALL
SELECT 564, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-halong-plaza.html' UNION ALL
SELECT 565, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-poseidon-ha-long.html' UNION ALL
SELECT 566, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-elizabeth.html' UNION ALL
SELECT 567, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-heritage-ha-noi.html' UNION ALL
SELECT 568, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-asian-ruby-luxury-sai-gon.html' UNION ALL
SELECT 569, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-oriental-sails.html' UNION ALL
SELECT 570, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-first-eden-ha-noi.html' UNION ALL
SELECT 571, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-pelican-ha-long.html' UNION ALL
SELECT 572, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-syrena-ha-long.html' UNION ALL
SELECT 573, 3, N'http://www.mangdatphong.vn/binh-thanh/sai-gon-view-residences.html' UNION ALL
SELECT 574, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-luxury-calypso-ha-long.html' UNION ALL
SELECT 575, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-star-view-ha-noi.html' UNION ALL
SELECT 576, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-caravella-ha-long.html' UNION ALL
SELECT 577, 3, N'http://www.mangdatphong.vn/halong/khach-san-halong-plaza.html' UNION ALL
SELECT 578, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-happy-ha-noi.html' UNION ALL
SELECT 579, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-nikko-saigon.html' UNION ALL
SELECT 580, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-starcity-suoi-mo-ha-long.html' UNION ALL
SELECT 581, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-sunshine-suite-ha-noi.html' UNION ALL
SELECT 582, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-flower-ha-noi.html' UNION ALL
SELECT 583, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-elizabeth.html' UNION ALL
SELECT 584, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-huong-hai-sealife.html' UNION ALL
SELECT 585, 3, N'http://www.mangdatphong.vn/da-nang/khach-san-golden-sea-da-nang.html' UNION ALL
SELECT 586, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-santa-barbara-ha-noi.html' UNION ALL
SELECT 587, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-bmc-thang-long.html' UNION ALL
SELECT 588, 3, N'http://www.mangdatphong.vn/district-1/khach-san-asian-ruby-luxury-sai-gon.html' UNION ALL
SELECT 589, 3, N'http://www.mangdatphong.vn/son-tra/khu-nghi-duong-fusion-maia-da-nang.html' UNION ALL
SELECT 590, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ha-noi-luxor.html' UNION ALL
SELECT 591, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-hidden-charm-ha-long.html' UNION ALL
SELECT 592, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-elegant-sai-gon.html' UNION ALL
SELECT 593, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-gold-da-nang.html' UNION ALL
SELECT 594, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-crown-ha-long.html' UNION ALL
SELECT 595, 3, N'http://www.mangdatphong.vn/ngu-hanh-son/khach-san-gold-2-da-nang.html' UNION ALL
SELECT 596, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-oasis-ha-noi.html' UNION ALL
SELECT 597, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-sunset-bay-da-nang.html' UNION ALL
SELECT 598, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-kingston-sai-gon.html' UNION ALL
SELECT 599, 3, N'http://www.mangdatphong.vn/hue/khach-san-river-view-hue.html' UNION ALL
SELECT 600, 3, N'http://www.mangdatphong.vn/ngu-hanh-son/khach-san-champa-da-nang.html' UNION ALL
SELECT 601, 3, N'http://www.mangdatphong.vn/district-1/khach-san-nikko-saigon.html' UNION ALL
SELECT 602, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-hoang-ngan-sai-gon.html' UNION ALL
SELECT 603, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-da-nang-riverside.html' UNION ALL
SELECT 604, 3, N'http://www.mangdatphong.vn/halong/khach-san-starcity-suoi-mo-ha-long.html' UNION ALL
SELECT 605, 3, N'http://www.mangdatphong.vn/hue/khach-san-century-riverside-hue.html' UNION ALL
SELECT 606, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-dai-nam-sai-gon.html' UNION ALL
SELECT 607, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-angel-da-nang.html' UNION ALL
SELECT 608, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-huong-hai-sealife.html' UNION ALL
SELECT 609, 3, N'http://www.mangdatphong.vn/hue/khach-san-mondial-hue.html' UNION ALL
SELECT 610, 3, N'http://www.mangdatphong.vn/halong/khach-san-bmc-thang-long.html' UNION ALL
SELECT 611, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-seventeen-saloon-da-nang.html' UNION ALL
SELECT 612, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-ngoc-bich-sai-gon.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 4.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 613, 3, N'http://www.mangdatphong.vn/hue/khach-san-new-star-hue.html' UNION ALL
SELECT 614, 3, N'http://www.mangdatphong.vn/halong/khach-san-hidden-charm-ha-long.html' UNION ALL
SELECT 615, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-princess-da-nang.html' UNION ALL
SELECT 616, 3, N'http://www.mangdatphong.vn/hue/khach-san-smile-hue.html' UNION ALL
SELECT 617, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-hong-thien-loc.html' UNION ALL
SELECT 618, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-varna-da-nang.html' UNION ALL
SELECT 619, 3, N'http://www.mangdatphong.vn/district-1/khach-san-elegant-sai-gon.html' UNION ALL
SELECT 620, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-sai-gon-sports-3.html' UNION ALL
SELECT 621, 3, N'http://www.mangdatphong.vn/halong/khach-san-crown-ha-long.html' UNION ALL
SELECT 622, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-star-da-nang.html' UNION ALL
SELECT 623, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-le-duy-sai-gon.html' UNION ALL
SELECT 624, 3, N'http://www.mangdatphong.vn/hue/khach-san-imperial-hue.html' UNION ALL
SELECT 625, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-hoa-dong-duong-sai-gon.html' UNION ALL
SELECT 626, 3, N'http://www.mangdatphong.vn/hue/khu-nghi-duong-sinh-thai-tam-giang.html' UNION ALL
SELECT 627, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-asian-ruby-3-saigon.html' UNION ALL
SELECT 628, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-trendy-da-nang.html' UNION ALL
SELECT 629, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-queen-ann.html' UNION ALL
SELECT 630, 3, N'http://www.mangdatphong.vn/hue/khach-san-villa-hue.html' UNION ALL
SELECT 631, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-hanriver-sai-gon.html' UNION ALL
SELECT 632, 3, N'http://www.mangdatphong.vn/binh-thanh/khach-san-saigon-domaine.html' UNION ALL
SELECT 633, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-monaco-da-nang.html' UNION ALL
SELECT 634, 3, N'http://www.mangdatphong.vn/hue/khach-san-xanh-hue.html' UNION ALL
SELECT 635, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-ha-my-2.html' UNION ALL
SELECT 636, 3, N'http://www.mangdatphong.vn/binh-thanh/faifoo-boutique-hotel.html' UNION ALL
SELECT 637, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-van-xuan-da-nang.html' UNION ALL
SELECT 638, 3, N'http://www.mangdatphong.vn/hue/khach-san-muong-thanh-hue.html' UNION ALL
SELECT 639, 3, N'http://www.mangdatphong.vn/binh-thanh/khach-san-apogee-sai-gon.html' UNION ALL
SELECT 640, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-ha-my-3-sai-gon.html' UNION ALL
SELECT 641, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-199-ho-chi-minh.html' UNION ALL
SELECT 642, 3, N'http://www.mangdatphong.vn/hue/khach-san-gold-hue.html' UNION ALL
SELECT 643, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-pelican-ha-long.html' UNION ALL
SELECT 644, 3, N'http://www.mangdatphong.vn/hue/khach-san-camellia-hue.html' UNION ALL
SELECT 645, 3, N'http://www.mangdatphong.vn/noi-dung/dieu-khoan-va-dieu-kien.html' UNION ALL
SELECT 646, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-syrena-ha-long.html' UNION ALL
SELECT 647, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-linh-dan-sai-gon.html' UNION ALL
SELECT 648, 3, N'http://www.mangdatphong.vn/hue/khach-san-midtown-hue.html' UNION ALL
SELECT 649, 3, N'http://www.mangdatphong.vn/district-1/khach-san-ha-my-3-sai-gon.html' UNION ALL
SELECT 650, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-caravella-ha-long.html' UNION ALL
SELECT 651, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-song-thuong-sai-gon.html' UNION ALL
SELECT 652, 3, N'http://www.mangdatphong.vn/district-1/khach-san-sai-gon-sports-3.html' UNION ALL
SELECT 653, 3, N'http://www.mangdatphong.vn/district-1/khach-san-ha-my-2.html' UNION ALL
SELECT 654, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vpbank.html' UNION ALL
SELECT 655, 3, N'http://www.mangdatphong.vn/district-1/khach-san-le-duy-sai-gon.html' UNION ALL
SELECT 656, 3, N'http://www.mangdatphong.vn/district-1/khach-san-kingston-sai-gon.html' UNION ALL
SELECT 657, 3, N'http://www.mangdatphong.vn/district-1/khach-san-asian-ruby-3-saigon.html' UNION ALL
SELECT 658, 3, N'http://www.mangdatphong.vn/noi-dung/chinh-sach-dai-ly.html' UNION ALL
SELECT 659, 3, N'http://www.mangdatphong.vn/district-1/khach-san-queen-ann.html' UNION ALL
SELECT 660, 3, N'http://www.mangdatphong.vn/district-1/khach-san-hoang-ngan-sai-gon.html' UNION ALL
SELECT 661, 3, N'http://www.mangdatphong.vn/noi-dung/chinh-sach-bao-mat.html' UNION ALL
SELECT 662, 3, N'http://www.mangdatphong.vn/district-1/khach-san-dai-nam-sai-gon.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 5.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 663, 3, N'http://www.mangdatphong.vn/noi-dung/lam-the-nao-toi-co-the-huy-booking.html' UNION ALL
SELECT 664, 3, N'http://www.mangdatphong.vn/district-1/khach-san-hong-thien-loc.html' UNION ALL
SELECT 665, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vietcombank.html' UNION ALL
SELECT 666, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-navibank.html' UNION ALL
SELECT 667, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-techcombank.html' UNION ALL
SELECT 668, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-sacombank.html' UNION ALL
SELECT 669, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vietabank.html' UNION ALL
SELECT 670, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-acb.html' UNION ALL
SELECT 671, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-eximbank.html' UNION ALL
SELECT 672, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-maritimebank.html' UNION ALL
SELECT 673, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vietinbank.html' UNION ALL
SELECT 674, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-mbank.html' UNION ALL
SELECT 675, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vibank.html' UNION ALL
SELECT 676, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-hd-bank.html' UNION ALL
SELECT 677, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/khach-san-havana-nha-trang-9515.html' UNION ALL
SELECT 678, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/white-sand-doclet-resort-spa-9503.html' UNION ALL
SELECT 679, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/white-sand-doclet-resort-spa-9503.html' UNION ALL
SELECT 680, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-nang/sandy-beach-resort-da-nang-9257.html' UNION ALL
SELECT 681, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/biet-thu-an-binh-9529.html' UNION ALL
SELECT 682, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/khach-san-havana-nha-trang-9515.html' UNION ALL
SELECT 683, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-bao-vung-tau-9276.html' UNION ALL
SELECT 684, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/some-days-of-silence-resort-spa-9779.html' UNION ALL
SELECT 685, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/biet-thu-an-binh-9529.html' UNION ALL
SELECT 686, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sai-gon-85-9510.html' UNION ALL
SELECT 687, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-bao-vung-tau-9276.html' UNION ALL
SELECT 688, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/some-days-of-silence-resort-spa-9779.html' UNION ALL
SELECT 689, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sai-gon-85-9510.html' UNION ALL
SELECT 690, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/merperle-resort-residences--9488.html' UNION ALL
SELECT 691, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sai-gon-85-9510.html' UNION ALL
SELECT 692, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/merperle-resort-residences--9488.html' UNION ALL
SELECT 693, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-bao-vung-tau-9276.html' UNION ALL
SELECT 694, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/yen-hideaway-resort-ngoc-suong-resort--9492.html' UNION ALL
SELECT 695, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-loc-9646.html' UNION ALL
SELECT 696, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-corvin-9500.html' UNION ALL
SELECT 697, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/sunrise-nha-trang-beach-hotel-spa-8225.html' UNION ALL
SELECT 698, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-doi-dua-9512.html' UNION ALL
SELECT 699, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-phan-vien-ngan-hang-9666.html' UNION ALL
SELECT 700, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-du-parc-da-lat-7781.html' UNION ALL
SELECT 701, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/merperle-resort-residences--9488.html' UNION ALL
SELECT 702, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-bao-thy-2-9642.html' UNION ALL
SELECT 703, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-doi-dua-9512.html' UNION ALL
SELECT 704, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/blue-moon-hotel-spa-9236.html' UNION ALL
SELECT 705, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-du-parc-da-lat-7781.html' UNION ALL
SELECT 706, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thang-muoi-9507.html' UNION ALL
SELECT 707, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-phan-vien-ngan-hang-9666.html' UNION ALL
SELECT 708, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-cao-bang-9577.html' UNION ALL
SELECT 709, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-la-sapinette-da-lat--9329.html' UNION ALL
SELECT 710, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/hue/khach-san-imperial-hue-8181.html' UNION ALL
SELECT 711, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/blue-moon-hotel-spa-9236.html' UNION ALL
SELECT 712, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thuy-duong-9647.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 6.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 713, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-golf-3-da-lat-9241.html' UNION ALL
SELECT 714, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/rum-vang-ii-hotel-9252.html' UNION ALL
SELECT 715, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/romana-resort-mui-ne-8341.html' UNION ALL
SELECT 716, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-duong-dong-vung-tau-9336.html' UNION ALL
SELECT 717, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-sammy-9242.html' UNION ALL
SELECT 718, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-vietsovpetro--7785.html' UNION ALL
SELECT 719, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-hoa-loc-9646.html' UNION ALL
SELECT 720, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-plaza-8862.html' UNION ALL
SELECT 721, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/osaka-resort-da-lat-9410.html' UNION ALL
SELECT 722, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/da-lat-cadasa-resort-9245.html' UNION ALL
SELECT 723, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-valley-mountain-8911.html' UNION ALL
SELECT 724, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/yen-hideaway-resort-ngoc-suong-resort--9492.html' UNION ALL
SELECT 725, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-best-western-da-lat-plaza-9244.html' UNION ALL
SELECT 726, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sai-gon-quy-nhon-8943.html' UNION ALL
SELECT 727, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/blue-moon-hotel-spa-9236.html' UNION ALL
SELECT 728, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-river-prince-da-lat-7782.html' UNION ALL
SELECT 729, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/mia-resort-9403.html' UNION ALL
SELECT 730, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-sai-gon-da-lat-9238.html' UNION ALL
SELECT 731, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/xuyen-moc/sai-gon-binh-chau-resort-9202.html' UNION ALL
SELECT 732, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/sunrise-nha-trang-beach-hotel-spa-8225.html' UNION ALL
SELECT 733, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-du-parc-da-lat-7781.html' UNION ALL
SELECT 734, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/xuyen-moc/huong-phong-ho-coc-resort-9212.html' UNION ALL
SELECT 735, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-bao-thy-2-9642.html' UNION ALL
SELECT 736, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/hue/khach-san-imperial-hue-8181.html' UNION ALL
SELECT 737, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thang-muoi-9507.html' UNION ALL
SELECT 738, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/romana-resort-mui-ne-8341.html' UNION ALL
SELECT 739, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/xuyen-moc/huong-phong-ho-coc-resort-9212.html' UNION ALL
SELECT 740, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/blue-moon-hotel-spa-9236.html' UNION ALL
SELECT 741, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sand-garden-resort-9548.html' UNION ALL
SELECT 742, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-nang/sandy-beach-resort-da-nang-9257.html' UNION ALL
SELECT 743, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-park-diamond-phan-thiet--8328.html' UNION ALL
SELECT 744, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sand-garden-resort-9548.html' UNION ALL
SELECT 745, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/hoi-an/the-nam-hai-9349.html' UNION ALL
SELECT 746, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/vinh-suong-seaside-resort-9547.html' UNION ALL
SELECT 747, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-park-diamond-phan-thiet--8328.html' UNION ALL
SELECT 748, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-y-linh-9377.html' UNION ALL
SELECT 749, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/apricot-resort-bau-mai-resort--9089.html' UNION ALL
SELECT 750, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sao-mai-resort--8314.html' UNION ALL
SELECT 751, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/non-nuoc-resort-phan-thiet-8318.html' UNION ALL
SELECT 752, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/-khu-nghi-duong-an-lam-vinh-ninh-van-bay-9405.html' UNION ALL
SELECT 753, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-the-palms-9597.html' UNION ALL
SELECT 754, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/hoi-an/the-nam-hai-9349.html' UNION ALL
SELECT 755, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/novela-resort-spa-8336.html' UNION ALL
SELECT 756, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-thao-ha-mui-ne-8316.html' UNION ALL
SELECT 757, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/merperle-resort-residences-best-western-premier-cu--9488.html' UNION ALL
SELECT 758, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-y-linh-9377.html' UNION ALL
SELECT 759, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/dynasty-beach-resort-8331.html' UNION ALL
SELECT 760, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-nang/sandy-beach-resort-da-nang-9257.html' UNION ALL
SELECT 761, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/golden-sail-hotel-restaurant-9161.html' UNION ALL
SELECT 762, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-plaza-8862.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 7.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 763, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-cali-quy-nhon-9370.html' UNION ALL
SELECT 764, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sandhills-beach-resort-spa-8334.html' UNION ALL
SELECT 765, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-du-parc-da-lat-mercure-du-parc-da-lat-truoc-day-7781.html' UNION ALL
SELECT 766, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/-khach-san-tuan-chau-morning-star-9562.html' UNION ALL
SELECT 767, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/green-coconut-resort-phan-thiet-9324.html' UNION ALL
SELECT 768, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/khach-san-havana-nha-trang-9515.html' UNION ALL
SELECT 769, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-asean-ha-long-8856.html' UNION ALL
SELECT 770, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sunflowers-quy-nhon-9131.html' UNION ALL
SELECT 771, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/-khu-nghi-duong-an-lam-vinh-ninh-van-bay-9405.html' UNION ALL
SELECT 772, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-sai-gon-ha-long-9112.html' UNION ALL
SELECT 773, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-corvin-9500.html' UNION ALL
SELECT 774, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/majestic-nha-trang--9486.html' UNION ALL
SELECT 775, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/vinh-suong-seaside-resort-9547.html' UNION ALL
SELECT 776, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-dc-9591.html' UNION ALL
SELECT 777, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sunflowers-quy-nhon-9131.html' UNION ALL
SELECT 778, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/apricot-resort-bau-mai-resort--9089.html' UNION ALL
SELECT 779, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/amiana-resort-nha-trang-9560.html' UNION ALL
SELECT 780, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-quy-nhon-9470.html' UNION ALL
SELECT 781, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-palace--8866.html' UNION ALL
SELECT 782, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-doi-duong-phan-thiet-9323.html' UNION ALL
SELECT 783, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-asean-ha-long-8856.html' UNION ALL
SELECT 784, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/non-nuoc-resort-phan-thiet-8318.html' UNION ALL
SELECT 785, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-binh-minh-9561.html' UNION ALL
SELECT 786, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-dream-9382.html' UNION ALL
SELECT 787, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/novela-resort-spa-8336.html' UNION ALL
SELECT 788, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-sai-gon-ha-long-9112.html' UNION ALL
SELECT 789, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sunrise-village-mui-ne-hotel-9325.html' UNION ALL
SELECT 790, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/green-hill-mui-ne-resort-spa--9243.html' UNION ALL
SELECT 791, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-valley-mountain-8911.html' UNION ALL
SELECT 792, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/hoang-kim-golden-resort-phan-thiet-9250.html' UNION ALL
SELECT 793, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/tien-phat-beach-resort-phan-thiet-8320.html' UNION ALL
SELECT 794, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/majestic-nha-trang--9486.html' UNION ALL
SELECT 795, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/peace-resort-mui-ne-9831.html' UNION ALL
SELECT 796, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sao-mai-resort--8314.html' UNION ALL
SELECT 797, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-the-coast-vung-tau-9295.html' UNION ALL
SELECT 798, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-dc-9591.html' UNION ALL
SELECT 799, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-ocean-front-tien-duong--9181.html' UNION ALL
SELECT 800, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/amiana-resort-nha-trang-9560.html' UNION ALL
SELECT 801, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-the-coast-vung-tau-9295.html' UNION ALL
SELECT 802, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/son-thuy-resort-9651.html' UNION ALL
SELECT 803, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/nha-trang/-khu-nghi-duong-an-lam-vinh-ninh-van-bay-9405.html' UNION ALL
SELECT 804, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/malibu-resort-9491.html' UNION ALL
SELECT 805, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/binh-minh-cable-car-resort-9813.html' UNION ALL
SELECT 806, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-romeliess-9182.html' UNION ALL
SELECT 807, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/dynasty-beach-resort-8331.html' UNION ALL
SELECT 808, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-palace--8866.html' UNION ALL
SELECT 809, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-cali-quy-nhon-9370.html' UNION ALL
SELECT 810, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sammy-vung-tau-9427.html' UNION ALL
SELECT 811, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ocean-side-9213.html' UNION ALL
SELECT 812, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-ha-long-dream-9382.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 8.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 813, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sao-mai-boutique-villas-9763.html' UNION ALL
SELECT 814, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-y-linh-9377.html' UNION ALL
SELECT 815, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/-royal-lotus-ha-long-8858.html' UNION ALL
SELECT 816, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-buu-dien-vung-tau-8908.html' UNION ALL
SELECT 817, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-eden-quy-nhon-9372.html' UNION ALL
SELECT 818, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thang-loi-victory--8905.html' UNION ALL
SELECT 819, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-quy-nhon-9470.html' UNION ALL
SELECT 820, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sai-gon-quy-nhon-8943.html' UNION ALL
SELECT 821, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/-khach-san-tuan-chau-morning-star-9562.html' UNION ALL
SELECT 822, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-green-vung-tau--9122.html' UNION ALL
SELECT 823, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/son-thuy-resort-9651.html' UNION ALL
SELECT 824, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-muong-thanh-quy-nhon-9374.html' UNION ALL
SELECT 825, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ocean-star-khach-san-sao-dai-duong--9282.html' UNION ALL
SELECT 826, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-grand-ha-long-9696.html' UNION ALL
SELECT 827, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-hai-au-quy-nhon-9373.html' UNION ALL
SELECT 828, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-the-palms-9597.html' UNION ALL
SELECT 829, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/binh-minh-cable-car-resort-9813.html' UNION ALL
SELECT 830, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/xuyen-moc/sai-gon-binh-chau-resort-9202.html' UNION ALL
SELECT 831, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-thao-ha-mui-ne-8316.html' UNION ALL
SELECT 832, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sammy-vung-tau-9427.html' UNION ALL
SELECT 833, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/green-hill-mui-ne-resort-spa--9243.html' UNION ALL
SELECT 834, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-cao-bang-9577.html' UNION ALL
SELECT 835, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/golden-sail-hotel-restaurant-9161.html' UNION ALL
SELECT 836, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-sao-mai-boutique-villas-9763.html' UNION ALL
SELECT 837, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/sandhills-beach-resort-spa-8334.html' UNION ALL
SELECT 838, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-la-sapinette-da-lat--9329.html' UNION ALL
SELECT 839, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/tien-phat-beach-resort-phan-thiet-8320.html' UNION ALL
SELECT 840, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-thi-thao-gardenia-da-lat--7776.html' UNION ALL
SELECT 841, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/green-coconut-resort-phan-thiet-9324.html' UNION ALL
SELECT 842, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thang-loi-victory--8905.html' UNION ALL
SELECT 843, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-empress-da-lat-7779.html' UNION ALL
SELECT 844, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-muong-thanh-quy-nhon-9374.html' UNION ALL
SELECT 845, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thuy-duong-9647.html' UNION ALL
SELECT 846, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-kieu-anh-8899.html' UNION ALL
SELECT 847, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-thi-thao-gardenia-da-lat--7776.html' UNION ALL
SELECT 848, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-cong-doan-vung-tau-9395.html' UNION ALL
SELECT 849, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-golf-3-da-lat-9241.html' UNION ALL
SELECT 850, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-hai-au-quy-nhon-9373.html' UNION ALL
SELECT 851, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-corvin-9500.html' UNION ALL
SELECT 852, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-anh-duong-7774.html' UNION ALL
SELECT 853, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-sai-gon-quy-nhon-8943.html' UNION ALL
SELECT 854, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/rum-vang-ii-hotel-9252.html' UNION ALL
SELECT 855, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-tulip-da-lat-9699.html' UNION ALL
SELECT 856, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-green-vung-tau--9122.html' UNION ALL
SELECT 857, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-seika-9216.html' UNION ALL
SELECT 858, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/thang-loi-1-7770.html' UNION ALL
SELECT 859, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-pho-nui-9587.html' UNION ALL
SELECT 860, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thanh-thuy-9607.html' UNION ALL
SELECT 861, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thuy-van-9508.html' UNION ALL
SELECT 862, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/villa-ngoc-my-9310.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 9.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 863, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-empress-da-lat-7779.html' UNION ALL
SELECT 864, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ocean-star-khach-san-sao-dai-duong--9282.html' UNION ALL
SELECT 865, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/khach-san-ocean-front-tien-duong--9181.html' UNION ALL
SELECT 866, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-kieu-anh-8899.html' UNION ALL
SELECT 867, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-mai-vang-9305.html' UNION ALL
SELECT 868, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/phan-thiet/malibu-resort-9491.html' UNION ALL
SELECT 869, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-grand-ha-long-9696.html' UNION ALL
SELECT 870, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/hoang-anh-dat-xanh-dalat-resort-9251.html' UNION ALL
SELECT 871, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-cong-doan-vung-tau-9395.html' UNION ALL
SELECT 872, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-crown-ha-long-8852.html' UNION ALL
SELECT 873, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-romeliess-9182.html' UNION ALL
SELECT 874, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-seika-9216.html' UNION ALL
SELECT 875, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-saphir-da-lat-9195.html' UNION ALL
SELECT 876, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ha-thanh-vung-tau-9339.html' UNION ALL
SELECT 877, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-van-hai--9497.html' UNION ALL
SELECT 878, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-pho-nui-9587.html' UNION ALL
SELECT 879, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-du-lich-dien-luc-9502.html' UNION ALL
SELECT 880, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-duong-dong-vung-tau-9336.html' UNION ALL
SELECT 881, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-ocean-side-9213.html' UNION ALL
SELECT 882, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-golf-1-7775.html' UNION ALL
SELECT 883, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/khach-san-new-star-ha-long-9710.html' UNION ALL
SELECT 884, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/ha-long/-royal-lotus-ha-long-8858.html' UNION ALL
SELECT 885, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-sammy-9242.html' UNION ALL
SELECT 886, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-saphir-da-lat-9195.html' UNION ALL
SELECT 887, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/reveto-villa-dalat-9021.html' UNION ALL
SELECT 888, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-vietsovpetro--7785.html' UNION ALL
SELECT 889, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-buu-dien-vung-tau-8908.html' UNION ALL
SELECT 890, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-thanh-binh-9309.html' UNION ALL
SELECT 891, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/osaka-resort-da-lat-9410.html' UNION ALL
SELECT 892, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-muong-thanh-vung-tau-9343.html' UNION ALL
SELECT 893, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-gold-night-9307.html' UNION ALL
SELECT 894, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/da-lat-cadasa-resort-9245.html' UNION ALL
SELECT 895, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/lan-rung-resort-spa-vung-tau-9351.html' UNION ALL
SELECT 896, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-lavy-7772.html' UNION ALL
SELECT 897, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-best-western-da-lat-plaza-9244.html' UNION ALL
SELECT 898, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/bimexco-resort-vung-tau-9200.html' UNION ALL
SELECT 899, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-thanh-thuy-9607.html' UNION ALL
SELECT 900, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-river-prince-da-lat-7782.html' UNION ALL
SELECT 901, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/villa-ngoc-my-9310.html' UNION ALL
SELECT 902, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/vung-tau/khach-san-valley-mountain-8911.html' UNION ALL
SELECT 903, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-mai-vang-9305.html' UNION ALL
SELECT 904, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/khach-san-sai-gon-da-lat-9238.html' UNION ALL
SELECT 905, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/quy-nhon/khach-san-eden-quy-nhon-9372.html' UNION ALL
SELECT 906, 1, N'http://www.yesgo.vn/vi/khach-san/viet-nam/da-lat/hoang-anh-dat-xanh-dalat-resort-9251.html' UNION ALL
SELECT 907, 3, N'http://www.mangdatphong.vn/thanh-xuan/khach-san-perfect-ha-noi.html' UNION ALL
SELECT 908, 3, N'http://www.mangdatphong.vn/thanh-xuan/khach-san-hoa-lam-ha-noi.html' UNION ALL
SELECT 909, 3, N'http://www.mangdatphong.vn/da-nang/khach-san-phuong-thu-da-nang.html' UNION ALL
SELECT 910, 3, N'http://www.mangdatphong.vn/thanh-xuan/khach-san-239-ha-noi.html' UNION ALL
SELECT 911, 3, N'http://www.mangdatphong.vn/dien-bien/him-lam-resort-dien-bien.html' UNION ALL
SELECT 912, 3, N'http://www.mangdatphong.vn/da-nang/the-ocean-villas-da-nang.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 10.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 913, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-luminous-viet-ha-noi.html' UNION ALL
SELECT 914, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-sai-gon-tourane-da-nang.html' UNION ALL
SELECT 915, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-twins-ha-noi.html' UNION ALL
SELECT 916, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-vinh-trung-plaza-da-nang.html' UNION ALL
SELECT 917, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-golden-silk-ha-noi.html' UNION ALL
SELECT 918, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-nu-hoang-da-nang.html' UNION ALL
SELECT 919, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-paramount.html' UNION ALL
SELECT 920, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-eden-plaza-da-nang.html' UNION ALL
SELECT 921, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-bao-ngan-ha-noi.html' UNION ALL
SELECT 922, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-tien-thinh-da-nang.html' UNION ALL
SELECT 923, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-eternity.html' UNION ALL
SELECT 924, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-dong-thanh-ha-noi.html' UNION ALL
SELECT 925, 3, N'http://www.mangdatphong.vn/ngu-hanh-son/khach-san-holiday-beach-da-nang.html' UNION ALL
SELECT 926, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-song-thu-da-nang.html' UNION ALL
SELECT 927, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-aquarius-legend-ha-noi.html' UNION ALL
SELECT 928, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-gia-bao-ha-noi.html' UNION ALL
SELECT 929, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-muong-thanh-da-nang.html' UNION ALL
SELECT 930, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-medallion-ha-noi.html' UNION ALL
SELECT 931, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-indochina-queen-2.html' UNION ALL
SELECT 932, 3, N'http://www.mangdatphong.vn/da-nang/khach-san-gold-coast-da-nang.html' UNION ALL
SELECT 933, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-quoc-cuong-ii-da-nang.html' UNION ALL
SELECT 934, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ha-noi-happy.html' UNION ALL
SELECT 935, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-cherie-da-nang.html' UNION ALL
SELECT 936, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ha-noi-hibiscus.html' UNION ALL
SELECT 937, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-phuong-dong-da-nang.html' UNION ALL
SELECT 938, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-tran-ha-noi.html' UNION ALL
SELECT 939, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-giany-da-nang.html' UNION ALL
SELECT 940, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-aphrodite-ha-long.html' UNION ALL
SELECT 941, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-flower-garden-ha-noi.html' UNION ALL
SELECT 942, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-tre-xanh-da-nang.html' UNION ALL
SELECT 943, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ho-guom-ha-noi.html' UNION ALL
SELECT 944, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-paloma-ha-long.html' UNION ALL
SELECT 945, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-lake-side-ha-noi.html' UNION ALL
SELECT 946, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-aphrodite-ha-long.html' UNION ALL
SELECT 947, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-splendid-jupiter.html' UNION ALL
SELECT 948, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-poseidon-ha-long.html' UNION ALL
SELECT 949, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-zen-ha-noi.html' UNION ALL
SELECT 950, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-halong-plaza.html' UNION ALL
SELECT 951, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-oriental-sails.html' UNION ALL
SELECT 952, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-royal-palace-ha-noi.html' UNION ALL
SELECT 953, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-skylark-ha-noi.html' UNION ALL
SELECT 954, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-pelican-ha-long.html' UNION ALL
SELECT 955, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-paloma-ha-long.html' UNION ALL
SELECT 956, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-luxury-calypso-ha-long.html' UNION ALL
SELECT 957, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-heritage-ha-noi.html' UNION ALL
SELECT 958, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-syrena-ha-long.html' UNION ALL
SELECT 959, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-poseidon-ha-long.html' UNION ALL
SELECT 960, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-elizabeth.html' UNION ALL
SELECT 961, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-first-eden-ha-noi.html' UNION ALL
SELECT 962, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-oriental-sails.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 11.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 963, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-caravella-ha-long.html' UNION ALL
SELECT 964, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-asian-ruby-luxury-sai-gon.html' UNION ALL
SELECT 965, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-star-view-ha-noi.html' UNION ALL
SELECT 966, 3, N'http://www.mangdatphong.vn/halong/khach-san-halong-plaza.html' UNION ALL
SELECT 967, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-starcity-suoi-mo-ha-long.html' UNION ALL
SELECT 968, 3, N'http://www.mangdatphong.vn/binh-thanh/sai-gon-view-residences.html' UNION ALL
SELECT 969, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-luxury-calypso-ha-long.html' UNION ALL
SELECT 970, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-happy-ha-noi.html' UNION ALL
SELECT 971, 3, N'http://www.mangdatphong.vn/ha-long/du-thuyen-huong-hai-sealife.html' UNION ALL
SELECT 972, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-nikko-saigon.html' UNION ALL
SELECT 973, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-elizabeth.html' UNION ALL
SELECT 974, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-flower-ha-noi.html' UNION ALL
SELECT 975, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-bmc-thang-long.html' UNION ALL
SELECT 976, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-sunshine-suite-ha-noi.html' UNION ALL
SELECT 977, 3, N'http://www.mangdatphong.vn/district-1/khach-san-asian-ruby-luxury-sai-gon.html' UNION ALL
SELECT 978, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-santa-barbara-ha-noi.html' UNION ALL
SELECT 979, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-hidden-charm-ha-long.html' UNION ALL
SELECT 980, 3, N'http://www.mangdatphong.vn/da-nang/khach-san-golden-sea-da-nang.html' UNION ALL
SELECT 981, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-elegant-sai-gon.html' UNION ALL
SELECT 982, 3, N'http://www.mangdatphong.vn/hoan-kiem/khach-san-ha-noi-luxor.html' UNION ALL
SELECT 983, 3, N'http://www.mangdatphong.vn/ha-long/khach-san-crown-ha-long.html' UNION ALL
SELECT 984, 3, N'http://www.mangdatphong.vn/son-tra/khu-nghi-duong-fusion-maia-da-nang.html' UNION ALL
SELECT 985, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-kingston-sai-gon.html' UNION ALL
SELECT 986, 3, N'http://www.mangdatphong.vn/ngu-hanh-son/khach-san-gold-2-da-nang.html' UNION ALL
SELECT 987, 3, N'http://www.mangdatphong.vn/ba-dinh/khach-san-oasis-ha-noi.html' UNION ALL
SELECT 988, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-gold-da-nang.html' UNION ALL
SELECT 989, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-hoang-ngan-sai-gon.html' UNION ALL
SELECT 990, 3, N'http://www.mangdatphong.vn/ngu-hanh-son/khach-san-champa-da-nang.html' UNION ALL
SELECT 991, 3, N'http://www.mangdatphong.vn/binh-thanh/khach-san-saigon-domaine.html' UNION ALL
SELECT 992, 3, N'http://www.mangdatphong.vn/hai-chau/khach-san-sunset-bay-da-nang.html' UNION ALL
SELECT 993, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-dai-nam-sai-gon.html' UNION ALL
SELECT 994, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-da-nang-riverside.html' UNION ALL
SELECT 995, 3, N'http://www.mangdatphong.vn/binh-thanh/faifoo-boutique-hotel.html' UNION ALL
SELECT 996, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-angel-da-nang.html' UNION ALL
SELECT 997, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-ngoc-bich-sai-gon.html' UNION ALL
SELECT 998, 3, N'http://www.mangdatphong.vn/binh-thanh/khach-san-apogee-sai-gon.html' UNION ALL
SELECT 999, 3, N'http://www.mangdatphong.vn/hue/khach-san-river-view-hue.html' UNION ALL
SELECT 1000, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-seventeen-saloon-da-nang.html' UNION ALL
SELECT 1001, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-hong-thien-loc.html' UNION ALL
SELECT 1002, 3, N'http://www.mangdatphong.vn/district-1/khach-san-nikko-saigon.html' UNION ALL
SELECT 1003, 3, N'http://www.mangdatphong.vn/hue/khach-san-century-riverside-hue.html' UNION ALL
SELECT 1004, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-princess-da-nang.html' UNION ALL
SELECT 1005, 3, N'http://www.mangdatphong.vn/halong/khach-san-starcity-suoi-mo-ha-long.html' UNION ALL
SELECT 1006, 3, N'http://www.mangdatphong.vn/hue/khach-san-mondial-hue.html' UNION ALL
SELECT 1007, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-varna-da-nang.html' UNION ALL
SELECT 1008, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-le-duy-sai-gon.html' UNION ALL
SELECT 1009, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-huong-hai-sealife.html' UNION ALL
SELECT 1010, 3, N'http://www.mangdatphong.vn/hue/khach-san-new-star-hue.html' UNION ALL
SELECT 1011, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-star-da-nang.html' UNION ALL
SELECT 1012, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-asian-ruby-3-saigon.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 12.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 1013, 3, N'http://www.mangdatphong.vn/halong/khach-san-bmc-thang-long.html' UNION ALL
SELECT 1014, 3, N'http://www.mangdatphong.vn/hue/khach-san-smile-hue.html' UNION ALL
SELECT 1015, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-trendy-da-nang.html' UNION ALL
SELECT 1016, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-queen-ann.html' UNION ALL
SELECT 1017, 3, N'http://www.mangdatphong.vn/halong/khach-san-hidden-charm-ha-long.html' UNION ALL
SELECT 1018, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-sai-gon-sports-3.html' UNION ALL
SELECT 1019, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-monaco-da-nang.html' UNION ALL
SELECT 1020, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-pelican-ha-long.html' UNION ALL
SELECT 1021, 3, N'http://www.mangdatphong.vn/district-1/khach-san-elegant-sai-gon.html' UNION ALL
SELECT 1022, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-hoa-dong-duong-sai-gon.html' UNION ALL
SELECT 1023, 3, N'http://www.mangdatphong.vn/son-tra/khach-san-van-xuan-da-nang.html' UNION ALL
SELECT 1024, 3, N'http://www.mangdatphong.vn/halong/khach-san-crown-ha-long.html' UNION ALL
SELECT 1025, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-syrena-ha-long.html' UNION ALL
SELECT 1026, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-hanriver-sai-gon.html' UNION ALL
SELECT 1027, 3, N'http://www.mangdatphong.vn/hue/khach-san-imperial-hue.html' UNION ALL
SELECT 1028, 3, N'http://www.mangdatphong.vn/district-1/khach-san-kingston-sai-gon.html' UNION ALL
SELECT 1029, 3, N'http://www.mangdatphong.vn/halong/du-thuyen-caravella-ha-long.html' UNION ALL
SELECT 1030, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-ha-my-2.html' UNION ALL
SELECT 1031, 3, N'http://www.mangdatphong.vn/hue/khu-nghi-duong-sinh-thai-tam-giang.html' UNION ALL
SELECT 1032, 3, N'http://www.mangdatphong.vn/district-1/khach-san-ha-my-2.html' UNION ALL
SELECT 1033, 3, N'http://www.mangdatphong.vn/quan-1/khach-san-ha-my-3-sai-gon.html' UNION ALL
SELECT 1034, 3, N'http://www.mangdatphong.vn/hue/khach-san-villa-hue.html' UNION ALL
SELECT 1035, 3, N'http://www.mangdatphong.vn/district-1/khach-san-ha-my-3-sai-gon.html' UNION ALL
SELECT 1036, 3, N'http://www.mangdatphong.vn/noi-dung/dieu-khoan-va-dieu-kien.html' UNION ALL
SELECT 1037, 3, N'http://www.mangdatphong.vn/hue/khach-san-xanh-hue.html' UNION ALL
SELECT 1038, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vpbank.html' UNION ALL
SELECT 1039, 3, N'http://www.mangdatphong.vn/hue/khach-san-muong-thanh-hue.html' UNION ALL
SELECT 1040, 3, N'http://www.mangdatphong.vn/noi-dung/chinh-sach-dai-ly.html' UNION ALL
SELECT 1041, 3, N'http://www.mangdatphong.vn/hue/khach-san-gold-hue.html' UNION ALL
SELECT 1042, 3, N'http://www.mangdatphong.vn/noi-dung/chinh-sach-bao-mat.html' UNION ALL
SELECT 1043, 3, N'http://www.mangdatphong.vn/hue/khach-san-camellia-hue.html' UNION ALL
SELECT 1044, 3, N'http://www.mangdatphong.vn/noi-dung/lam-the-nao-toi-co-the-huy-booking.html' UNION ALL
SELECT 1045, 3, N'http://www.mangdatphong.vn/hue/khach-san-midtown-hue.html' UNION ALL
SELECT 1046, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vietcombank.html' UNION ALL
SELECT 1047, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-navibank.html' UNION ALL
SELECT 1048, 3, N'http://www.mangdatphong.vn/district-1/khach-san-sai-gon-sports-3.html' UNION ALL
SELECT 1049, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-techcombank.html' UNION ALL
SELECT 1050, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-199-ho-chi-minh.html' UNION ALL
SELECT 1051, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-sacombank.html' UNION ALL
SELECT 1052, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-linh-dan-sai-gon.html' UNION ALL
SELECT 1053, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vietabank.html' UNION ALL
SELECT 1054, 3, N'http://www.mangdatphong.vn/phu-nhuan/khach-san-song-thuong-sai-gon.html' UNION ALL
SELECT 1055, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-acb.html' UNION ALL
SELECT 1056, 3, N'http://www.mangdatphong.vn/district-1/khach-san-hoang-ngan-sai-gon.html' UNION ALL
SELECT 1057, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-eximbank.html' UNION ALL
SELECT 1058, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-maritimebank.html' UNION ALL
SELECT 1059, 3, N'http://www.mangdatphong.vn/district-1/khach-san-dai-nam-sai-gon.html' UNION ALL
SELECT 1060, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vietinbank.html' UNION ALL
SELECT 1061, 3, N'http://www.mangdatphong.vn/district-1/khach-san-hong-thien-loc.html' UNION ALL
SELECT 1062, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-mbank.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 13.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[LinkDetail]([LinkDetailID], [WebsiteID], [Url])
SELECT 1063, 3, N'http://www.mangdatphong.vn/district-1/khach-san-le-duy-sai-gon.html' UNION ALL
SELECT 1064, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-vibank.html' UNION ALL
SELECT 1065, 3, N'http://www.mangdatphong.vn/district-1/khach-san-asian-ruby-3-saigon.html' UNION ALL
SELECT 1066, 3, N'http://www.mangdatphong.vn/noi-dung/huong-dan-thanh-toan-bang-the-atm-cua-ngan-hang-hd-bank.html' UNION ALL
SELECT 1067, 3, N'http://www.mangdatphong.vn/district-1/khach-san-queen-ann.html'
COMMIT;
RAISERROR (N'[dbo].[LinkDetail]: Insert Batch: 14.....Done!', 10, 1) WITH NOWAIT;
GO

SET IDENTITY_INSERT [dbo].[LinkDetail] OFF;

BEGIN TRANSACTION;
INSERT INTO [dbo].[Hotel_Properties]([WebsiteID], [PropertyID], [Selector], [ResultMode], [AfterRegex])
SELECT 1, 1, N'.nav_sub .current a', N'text', NULL UNION ALL
SELECT 1, 2, N'.hotel_detail1 .de2 .gray_color.text_i', N'text', N'^.*?$' UNION ALL
SELECT 1, 3, N'.nav_sub ul li:nth-child(3) a', N'text', NULL UNION ALL
SELECT 1, 4, N'div#roomList + .box_info_hotel .tex_content', N'text', NULL UNION ALL
SELECT 1, 5, N'.hotel_detail1 .de2_more h1 img', N'attr(''src'')', N'(?<=\/star)\d(?=\.png)' UNION ALL
SELECT 1, 7, N'.hotel_detail1 .box_assessment b', N'text', N'\d\.\d' UNION ALL
SELECT 1, 8, N'#roomList table tr:not(:first-child)', N'block', NULL UNION ALL
SELECT 3, 1, N'h1.title', N'text', N'^.*?(?=\[)' UNION ALL
SELECT 3, 2, N'div.add', N'text', N'(?<=:).*?(?=\()' UNION ALL
SELECT 3, 3, N'.hotel-detail-path li:nth-child(3)', N'text', NULL UNION ALL
SELECT 3, 4, N'.info_product', N'text', NULL UNION ALL
SELECT 3, 5, N'h1.title img', N'size', NULL UNION ALL
SELECT 3, 6, N'.lazy', N'attr(''data-src'')', NULL UNION ALL
SELECT 3, 7, N'h4[itemprop=average]', N'text', NULL UNION ALL
SELECT 3, 8, N'table.table-room-type tr:not([valign=top]):not(:first-child)', N'block', NULL
COMMIT;
RAISERROR (N'[dbo].[Hotel_Properties]: Insert Batch: 1.....Done!', 10, 1) WITH NOWAIT;
GO

BEGIN TRANSACTION;
INSERT INTO [dbo].[Room_Properties]([WebsiteID], [PropertyID], [Selector], [ResultMode], [AfterRegex])
SELECT 1, 9, N'.m20t', N'text', NULL UNION ALL
SELECT 1, 10, N'.tTip img', N'size', NULL UNION ALL
SELECT 1, 11, N'.font16 strong', N'text', N'[\d\.]+' UNION ALL
SELECT 1, 12, N'.room_type_l img', N'attr(''src'')', NULL UNION ALL
SELECT 1, 13, N'.room_type_2 .p5t.font11', N'text', NULL UNION ALL
SELECT 3, 9, N'td img', N'text', NULL UNION ALL
SELECT 3, 10, N'td:nth-child(1) img', N'size', NULL UNION ALL
SELECT 3, 11, N'.hotel-availability-rate', N'text', NULL UNION ALL
SELECT 3, 12, N'.notavailable', N'text', NULL UNION ALL
SELECT 3, 13, N'.notavailable', N'text', NULL
COMMIT;
RAISERROR (N'[dbo].[Room_Properties]: Insert Batch: 1.....Done!', 10, 1) WITH NOWAIT;
GO

