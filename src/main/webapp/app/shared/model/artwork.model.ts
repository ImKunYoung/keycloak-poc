import { IArtworkComment } from 'app/shared/model/artwork-comment.model';
import { IArtworkView } from 'app/shared/model/artwork-view.model';
import { IArtworkLike } from 'app/shared/model/artwork-like.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IArtwork {
  id?: number;
  title?: string | null;
  shortDescription?: string | null;
  longDescription?: string | null;
  imageUrl?: string | null;
  artistname?: string | null;
  makingday?: string | null;
  status?: Status | null;
  artworkcomments?: IArtworkComment[] | null;
  artworkviews?: IArtworkView[] | null;
  artworklikes?: IArtworkLike[] | null;
}

export const defaultValue: Readonly<IArtwork> = {};
