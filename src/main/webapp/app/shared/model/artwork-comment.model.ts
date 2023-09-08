import { IArtwork } from 'app/shared/model/artwork.model';

export interface IArtworkComment {
  id?: number;
  member?: number | null;
  content?: string | null;
  artwork?: IArtwork | null;
}

export const defaultValue: Readonly<IArtworkComment> = {};
