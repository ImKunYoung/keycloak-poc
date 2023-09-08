import { IArtwork } from 'app/shared/model/artwork.model';

export interface IArtworkView {
  id?: number;
  member?: number | null;
  artwork?: IArtwork | null;
}

export const defaultValue: Readonly<IArtworkView> = {};
