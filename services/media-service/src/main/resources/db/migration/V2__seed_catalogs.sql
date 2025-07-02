-- Insert into languages
INSERT INTO public.languages (name, created_at, updated_at, created_by, updated_by) VALUES
  ('Chinese', now(), now(), 'system', 'system'),
  ('English', now(), now(), 'system', 'system'),
  ('Japanese', now(), now(), 'system', 'system'),
  ('Korean', now(), now(), 'system', 'system'),
  ('Spanish', now(), now(), 'system', 'system');

-- Insert into media_status
INSERT INTO public.media_status (name, created_at, updated_at, created_by, updated_by) VALUES
  ('Axed', now(), now(), 'system', 'system'),
  ('Completed', now(), now(), 'system', 'system'),
  ('Hiatus', now(), now(), 'system', 'system'),
  ('OnGoing', now(), now(), 'system', 'system');

-- Insert into media_types
INSERT INTO public.media_types (name, created_at, updated_at, created_by, updated_by) VALUES
  ('Comic', now(), now(), 'system', 'system'),
  ('Manga', now(), now(), 'system', 'system'),
  ('Webtoon', now(), now(), 'system', 'system');